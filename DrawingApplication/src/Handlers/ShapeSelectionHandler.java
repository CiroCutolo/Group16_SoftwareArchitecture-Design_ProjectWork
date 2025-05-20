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

    /**
     * Gestisce la selezione di una forma in base all'interazione con il mouse.
     * Se il tasto sinistro è premuto, seleziona la forma sotto al cursore (se presente),
    * applicando l'effetto visivo.
    * @param event evento del mouse
    * @param drawShapes lista delle forme attualmente disegnate
    * @param drawingPane area di disegno in cui si trovano le forme
     */
    public void handleSelection(MouseEvent event, List<Shape> drawShapes, Pane drawingPane) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double x = event.getX();
            double y = event.getY();
            Shape newSelected = null;

            for (int i = drawShapes.size() - 1; i >= 0; i--) {
                Shape shape = drawShapes.get(i);
                if (shape.toFXShape().contains(x, y)) {
                    newSelected = shape;
                    break;
                }
            }

            applyVisualSelection(newSelected);
        }
    }

    /**
     * Metodo dedito all'attivazione e disattivazione degli effetti visivi legati alla selezione
     * 
     * @param shape forma selezionata a cui applicare o da cui rimuovere l'effetto visivo di selezione
     * 
     * @author ciroc
     */
    public void applyVisualSelection(Shape shape) {
        if (selectedShape != null) {
            deselectShape(selectedShape.getFXShape());
        }

        selectedShape = shape;

        if (selectedShape != null) {
            selectShape(selectedShape.getFXShape());
        }
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
           deselectShape(selectedShape.getFXShape());
           selectedShape = null;
       }
   }

}
