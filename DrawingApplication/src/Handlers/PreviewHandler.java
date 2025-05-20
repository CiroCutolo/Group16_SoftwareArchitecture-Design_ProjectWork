/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Shapes.Shape;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Sterm
 */

/**
 * La classe PreviewHandler gestisce la logica per la visualizzazione
 * dell'anteprima di una forma durante il trascinamento del mouse.
 * Permette di costruire dinamicamente una forma visiva (non definitiva)
 * e di finalizzarla al rilascio del mouse.
 * 
 */
public class PreviewHandler {

    private double startX, startY;
    private javafx.scene.shape.Shape previewShape;

    /**
     * Registra la posizione iniziale del mouse alla pressione,
     * da cui partirà il disegno della forma in anteprima.
     *
     * @param event evento di pressione del mouse
     */
    public void handleMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
    }

    /**
     * Durante il trascinamento del mouse, aggiorna la forma in anteprima
     * basandosi sulla posizione corrente del cursore. La forma viene disegnata
     * con tratteggio grigio e trasparente per distinguerla da una forma definitiva.
     *
     * @param event evento di trascinamento del mouse
     * @param shapeType tipo di forma da creare (es. "RECTANGLE", "LINE")
     * @param drawingPane area di disegno in cui disegnare l'anteprima
     */
    public void handleMouseDragged(MouseEvent event, String shapeType, Pane drawingPane) {
        double endX = Math.max(0, Math.min(event.getX(), drawingPane.getWidth()));
        double endY = Math.max(0, Math.min(event.getY(), drawingPane.getHeight()));

        Shape temp = Shapes.ShapeFactory.createShape(shapeType, startX, startY, endX, endY);
        javafx.scene.shape.Shape fxTemp = temp.toFXShape();
        fxTemp.getStrokeDashArray().addAll(5.0, 5.0);
        fxTemp.setStroke(Color.GRAY);
        fxTemp.setFill(Color.TRANSPARENT);

        if (previewShape != null) {
            drawingPane.getChildren().remove(previewShape);
        }

        previewShape = fxTemp;
        drawingPane.getChildren().add(previewShape);
    }

    /**
     * Al rilascio del mouse, rimuove l'anteprima dal pannello e
     * crea una forma definitiva con i colori specificati.
     *
     * @param event evento di rilascio del mouse
     * @param shapeType tipo di forma da creare
     * @param drawingPane pannello di disegno
     * @param strokeColor colore del bordo
     * @param fillColor colore di riempimento
     * @return la forma logica creata
     */
    public Shape handleMouseReleased(MouseEvent event, String shapeType, Pane drawingPane, Color strokeColor, Color fillColor) {
        double endX = Math.max(0, Math.min(event.getX(), drawingPane.getWidth()));
        double endY = Math.max(0, Math.min(event.getY(), drawingPane.getHeight()));

        if (previewShape != null) {
            drawingPane.getChildren().remove(previewShape);
            previewShape = null;
        }

        Shape finalShape = Shapes.ShapeFactory.createShape(shapeType, startX, startY, endX, endY, strokeColor, fillColor);
        return finalShape;
    }
}
