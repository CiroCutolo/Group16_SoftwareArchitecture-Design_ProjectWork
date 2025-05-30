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
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

/**
 *
 * @author Sterm, ciroc
 */


/**
 * La classe ShapeSelectionHandler gestisce la selezione logica e visiva
 * delle forme disegnate nel canvas. Permette di applicare e rimuovere
 * effetti visivi alle shape selezionate, mantenendo una sola selezione attiva.
 * 
 */
public class ShapeSelectionHandler {

    // SELEZIONE
    private Shape selectedShape = null;
    private final List<Shape> selectedShapes = new ArrayList<>();

    // SELEZIONE MULTIPLA
    private javafx.scene.shape.Rectangle selectionArea = null;
    private boolean isDraggingForSelection = false;
    private double selectionStartingPointX;
    private double selectionStartingPointY;

    // TRASCINAMENTO 
    private double draggingPointX;
    private double draggingPointY;

    // STATO 
    private final DrawingStateHistory history;

    public ShapeSelectionHandler(DrawingStateHistory history) {
        this.history = history;
    }

    /*
    ----------------------------------
     METODI PUBBLICI: GESTIONE EVENTI
    ----------------------------------
    */
    /**
     * @author ciroc
     * @param e
     * @param drawingPane 
     */
    public void onMousePressed(MouseEvent e, Pane drawingPane) {
        if (e.getButton() == MouseButton.PRIMARY) {
            beginSelectionRectangle(e, drawingPane);
        }
    }

    /**
     * @author ciroc
     * @param e 
     */
    public void onMouseDragged(MouseEvent e) {
        if (isDraggingForSelection) {
            updateSelectionArea(e);
        }
    }

    /**
     * @author ciroc
     * @param e
     * @param drawShapes
     * @param drawingPane 
     */
    public void onMouseReleased(MouseEvent e, List<Shape> drawShapes, Pane drawingPane) {
        if (isDraggingForSelection) {
            finalizeSelectionArea(e, drawShapes, drawingPane);
        }
    }

    /**
    * Gestisce la selezione della forma
    * @param event evento del mouse
    * @param drawShapes lista delle forme attualmente disegnate
    * @param drawingPane area di disegno in cui si trovano le forme
    */
    public void handleSelection(MouseEvent event, List<Shape> drawShapes, Pane drawingPane) {
        Shape shape = findShapeSelectedShapePoint(drawShapes, event.getSceneX(), event.getSceneY());
        applySingleSelection(shape, drawingPane);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape shape) {
        this.selectedShape = shape;
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

        for (Shape shape : selectedShapes) {
            deselectShape(shape.getFXShape());
            removeDragListeners(shape);
        }
        selectedShapes.clear();
    }

    /* 
    ----------------------------------
        METODI PRIVATI: SELEZIONE
    ----------------------------------
    */
    
    /**
     * @author ciroc
     * 
     * Applica la selezione singola a una forma, deselezionando tutte le altre.
     * @param shape la forma da selezionare
     * @param drawingPane il pannello di disegno
     */
    private void applySingleSelection(Shape shape, Pane drawingPane) {
        clearSelection();

        if (shape != null) {
            selectedShape = shape;
            visualShapeSelectionEffect(shape.getFXShape());
            addDragListeners(shape, drawingPane);
        }
    }

    /**
     * @author ciroc
     * 
     * Trova la prima forma che contiene il punto cliccato per la selezione(sceneX, sceneY).
     * @param shapes lista delle forme disegnate
     * @param sceneX coordinata X
     * @param sceneY coordinata Y
     * @return la shape trovata o null se nessuna forma è presente nel punto
     */
    private Shape findShapeSelectedShapePoint(List<Shape> shapes, double sceneX, double sceneY) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            javafx.scene.shape.Shape fx = shapes.get(i).getFXShape();
            if (fx.contains(fx.sceneToLocal(sceneX, sceneY))) {
                return shapes.get(i);
            }
        }
        return null;
    }

    /**
     * Applica l'effetto visivo di selezione alla forma selezionata.
     * @param fxShape la forma a cui applicare l'effetto
     */
    private void visualShapeSelectionEffect(javafx.scene.shape.Shape fxShape) {
        DropShadow selection = new DropShadow();
        selection.setColor(Color.BLACK);
        selection.setRadius(15);
        fxShape.setEffect(selection);
    }

    /**
     * Rimuove l'effetto visivo di selezione dalla forma selezionata.
     * @param fxShape la forma a cui applicare l'effetto
     */
    private void deselectShape(javafx.scene.shape.Shape fxShape) {
        fxShape.setEffect(null);
    }

    /*
    ----------------------------------
    METODI PRIVATI: SELEZIONE MULTIPLA
    ----------------------------------
    */
    
    /**
     * Cattura il punto iniziale dell'area di selezione multipla.
     * @param e evento del mouse
     * @param drawingPane pannello su cui disegnare l'area di selezione
     */
    private void beginSelectionRectangle(MouseEvent e, Pane drawingPane) {
        selectionStartingPointX = e.getX();
        selectionStartingPointY = e.getY();

        selectionArea = new javafx.scene.shape.Rectangle();
        selectionArea.setX(selectionStartingPointX);
        selectionArea.setY(selectionStartingPointY);
        selectionArea.setFill(Color.web("blue", 0.2));
        selectionArea.setStroke(Color.LIGHTGRAY);
        selectionArea.getStrokeDashArray().addAll(5.0, 5.0);
        drawingPane.getChildren().add(selectionArea);

        isDraggingForSelection = true;
        clearSelection();
    }

    /**
     * Aggiorna le dimensioni dell'area di selezione in tempo reale, in base alla posizione del mouse.
     * @param e evento del mouse
     */
    private void updateSelectionArea(MouseEvent e) {
        double currentX = e.getX();
        double currentY = e.getY();
        double width = Math.abs(currentX - selectionStartingPointX);
        double height = Math.abs(currentY - selectionStartingPointY);

        selectionArea.setX(Math.min(currentX, selectionStartingPointX));
        selectionArea.setY(Math.min(currentY, selectionStartingPointY));
        selectionArea.setWidth(width);
        selectionArea.setHeight(height);
    }

    /**
     * Serve a selezionare tutte le forme contenute nell'area di selezione,
     * una volta finalizzata la selezione stessa.
     * Se il drag è minimo, effettua una selezione singola.
     * @param e evento del mouse
     * @param drawShapes lista delle forme disegnate
     * @param drawingPane pannello di disegno
     */
    private void finalizeSelectionArea(MouseEvent e, List<Shape> drawShapes, Pane drawingPane) {
        double dragDistance = Math.hypot(e.getX() - selectionStartingPointX, e.getY() - selectionStartingPointY);

        if (dragDistance > 5) {
            Bounds selectionBounds = selectionArea.getBoundsInParent();
            for (Shape shape : drawShapes) {
                if (selectionBounds.contains(shape.getFXShape().getBoundsInParent())) {
                    selectedShapes.add(shape);
                    visualShapeSelectionEffect(shape.getFXShape());
                    addDragListeners(shape, drawingPane);
                }
            }
        } else {
            Shape clickedShape = findShapeSelectedShapePoint(drawShapes, e.getSceneX(), e.getSceneY());
            applySingleSelection(clickedShape, drawingPane);
        }

        drawingPane.getChildren().remove(selectionArea);
        selectionArea = null;
        isDraggingForSelection = false;
    }

    /*
    ----------------------------------
       METODI PRIVATI: TRASCINAMENTO
    ----------------------------------
    */
    
    /**
     * Aggiunge listener per il trascinamento alla forma specificata.
     * Consente di spostare la forma con il mouse e registra l'operazione nella cronologia.
     * @param shape la forma da rendere trascinabile
     * @param drawingPane pannello di disegno
     */
    private void addDragListeners(Shape shape, Pane drawingPane) {
        javafx.scene.shape.Shape fxShape = shape.getFXShape();

        fxShape.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                draggingPointX = e.getSceneX();
                draggingPointY = e.getSceneY();
                fxShape.setUserData(new double[]{fxShape.getTranslateX(), fxShape.getTranslateY()});
                e.consume();
            }
        });

        fxShape.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double dx = e.getSceneX() - draggingPointX;
                double dy = e.getSceneY() - draggingPointY;

                Bounds bounds = fxShape.getBoundsInParent();
                double newMinX = bounds.getMinX() + dx;
                double newMinY = bounds.getMinY() + dy;

                if (newMinX < 0) dx -= newMinX;
                if (newMinY < 0) dy -= newMinY;

                shape.moveBy(dx, dy);
                draggingPointX = e.getSceneX();
                draggingPointY = e.getSceneY();

                expandCanvasIfNeeded(bounds, dx, dy, drawingPane);
                e.consume();
            }
        });

        fxShape.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double[] start = (double[]) fxShape.getUserData();
                double dx = fxShape.getTranslateX() - start[0];
                double dy = fxShape.getTranslateY() - start[1];
                if (dx != 0 || dy != 0) {
                    history.push(new MoveShapeCommand(shape, dx, dy));
                }
                e.consume();
            }
        });
    }

    /**
     * Rimuove tutti i listener utili al trascinamento della forma.
     * @param shape la forma da disassociare
     */
    private void removeDragListeners(Shape shape) {
        javafx.scene.shape.Shape fx = shape.getFXShape();
        fx.setOnMousePressed(null);
        fx.setOnMouseDragged(null);
        fx.setOnMouseReleased(null);
    }

    /**
     * Espande il riquadro, automaticamente, se la forma trascinata supera i limiti
     * imposti dai bordi visibili.
     * 
     * @param bounds i limiti della forma
     * @param dx spostamento in X
     * @param dy spostamento in Y
     * @param drawingPane pannello di disegno
     */
    private void expandCanvasIfNeeded(Bounds bounds, double dx, double dy, Pane drawingPane) {
        double padding = 100;
        double newMaxX = bounds.getMaxX() + dx;
        double newMaxY = bounds.getMaxY() + dy;

        if (newMaxX >= drawingPane.getWidth() - padding) {
            drawingPane.setPrefWidth(newMaxX + padding);
        }
        if (newMaxY >= drawingPane.getHeight() - padding) {
            drawingPane.setPrefHeight(newMaxY + padding);
        }
    }
}

