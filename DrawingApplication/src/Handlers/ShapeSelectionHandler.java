/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Shapes.Shape;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;

import Command.MoveShapeCommand;
import Handlers.DrawingStateHistory;
import javafx.geometry.Bounds;
/**
 *
 * @author Sterm
 */


/**
 * La classe ShapeSelectionHandler gestisce la selezione logica e visiva
 * delle forme disegnate nel canvas. Permette di applicare e rimuovere
 * effetti visivi alle shape selezionate, mantenendo una sola selezione attiva.
 * 
 */
public class ShapeSelectionHandler {

    private Shape selectedShape = null;
    private double dragAnchorX;
    private double dragAnchorY;
    private final DrawingStateHistory history;
    
    public ShapeSelectionHandler(DrawingStateHistory history) {
        this.history = history;
    }

    /**
     * Gestisce la selezione di una forma in base all'interazione con il mouse.
     * Se il tasto sinistro è premuto, seleziona la forma sotto al cursore (se presente),
    * applicando l'effetto visivo.
    * @param event evento del mouse
    * @param drawShapes lista delle forme attualmente disegnate
    * @param drawingPane area di disegno in cui si trovano le forme
     */
    public void handleSelection(MouseEvent event, List<Shape> drawShapes, Pane drawingPane) {
        
        double x = event.getSceneX();
        double y = event.getSceneY();
        Shape newSelected = null;

        for (int i = drawShapes.size() - 1; i >= 0; i--) {
            Shape shape = drawShapes.get(i);
            javafx.scene.shape.Shape fx = shape.getFXShape();
            if (fx.contains(fx.sceneToLocal(x,y))) {
                newSelected = shape;
                break;
            }
        }

        applyVisualSelection(newSelected,drawingPane);
    }

    /**
     * Metodo dedito all'attivazione e disattivazione degli effetti visivi legati alla selezione
     * 
     * @param shape forma selezionata a cui applicare o da cui rimuovere l'effetto visivo di selezione
     * 
     * @author ciroc
     */
    public void applyVisualSelection(Shape shape, Pane drawingPane) {
        if (selectedShape != null) {
            removeDragListeners(selectedShape);
            deselectShape(selectedShape.getFXShape());
        }

        selectedShape = shape;

        if (selectedShape != null) {
            selectShape(selectedShape.getFXShape());
            addDragListeners(selectedShape, drawingPane);
        }
    }
    
    /** Attacca i listener di trascinamento alla shape selezionata */
    private void addDragListeners(Shape logicalShape, Pane drawingPane) {
        javafx.scene.shape.Shape fx = logicalShape.getFXShape();

        fx.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                dragAnchorX = e.getSceneX();
                dragAnchorY = e.getSceneY();
                // memorizza la posizione di partenza
                fx.setUserData(new double[]{fx.getTranslateX(), fx.getTranslateY()});
                e.consume();
            }
        });

        fx.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double dx = e.getSceneX() - dragAnchorX;
                double dy = e.getSceneY() - dragAnchorY;
                
                javafx.scene.shape.Shape fxShape = logicalShape.getFXShape();
                Bounds bounds = fxShape.getBoundsInParent();

                // Calcolo solo i nuovi bounds, senza applicare trasformazioni
                double newMinX = bounds.getMinX() + dx;
                double newMinY = bounds.getMinY() + dy;

                // Se il nuovo bordo sinistro sarebbe < 0, annulla solo dx negativo
                if (newMinX < 0) {
                    dx -= newMinX; // sposta solo quel che resta dentro
                }

                // Se il nuovo bordo superiore sarebbe < 0, annulla solo dy negativo
                if (newMinY < 0) {
                    dy -= newMinY;
                }

                
                logicalShape.moveBy(dx, dy);
                dragAnchorX = e.getSceneX();
                dragAnchorY = e.getSceneY();
                
                // Controllo espansione canvas mentre sposti
                //Todo - funzione simile a DynamicUpdate del controller
                double padding = 100;
                double newMaxX = bounds.getMaxX() + dx;
                double newMaxY = bounds.getMaxY() + dy;

                double currentWidth = drawingPane.getWidth();
                double currentHeight = drawingPane.getHeight();

                if (newMaxX >= currentWidth - padding) {
                    drawingPane.setPrefWidth(newMaxX + padding);
                }

                if (newMaxY >= currentHeight - padding) {
                    drawingPane.setPrefHeight(newMaxY + padding);
                }

                
                e.consume();
            }
        });

        fx.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double[] start = (double[]) fx.getUserData();
                double dx = fx.getTranslateX() - start[0];
                double dy = fx.getTranslateY() - start[1];
                if (dx != 0 || dy != 0) {
                    // registra nello stack Undo
                    history.push(new MoveShapeCommand(logicalShape, dx, dy));
                }
                e.consume();
            }
        });
    }


    /** Rimuove i listener dalla shape (per evitare memory-leak) */
    private void removeDragListeners(Shape logicalShape) {
        javafx.scene.shape.Shape fx = logicalShape.getFXShape();
        fx.setOnMousePressed(null);
        fx.setOnMouseDragged(null);
        fx.setOnMouseReleased(null);
    }


     /**
     * Metodo dedito all'applicazione degli effetti visivi legati alla selezione
     * 
     * @param shape forma su cui azionare effetti visivi per la selezione
     * 
     * @author ciroc
     */
    private void selectShape(javafx.scene.shape.Shape shape) {
        DropShadow selection = new DropShadow();
        selection.setColor(javafx.scene.paint.Color.BLACK);
        selection.setRadius(15);
        shape.setEffect(selection);
    }

    /**
     * Metodo dedito alla rimozione degli effetti visivi legati alla selezione
     * 
     * @param shape forma da cui devono essere rimossi gli effetti visivi di selezione
     * 
     * @author ciroc
     */
    private void deselectShape(javafx.scene.shape.Shape shape) {
        shape.setEffect(null);
    }

    
    /**
    * Restituisce la forma attualmente selezionata.
    *
    * @return la forma selezionata, oppure null se nessuna è selezionata
    */
   public Shape getSelectedShape() {
       return selectedShape;
   }

   /**
    * Imposta manualmente la forma selezionata.
    * Non applica effetti visivi ma solo aggiornamento logico.
    *
    * @param selectedShape la forma da considerare come selezionata
    */
   public void setSelectedShape(Shape selectedShape) {
       this.selectedShape = selectedShape;
   }

   /**
    * Annulla la selezione corrente, rimuovendo eventuali effetti visivi
    * e mettendo a null il riferimento alla forma selezionata.
    */
   public void clearSelection() {
       if (selectedShape != null) {
           removeDragListeners(selectedShape);
           deselectShape(selectedShape.getFXShape());
           selectedShape = null;
       }
   }

}
