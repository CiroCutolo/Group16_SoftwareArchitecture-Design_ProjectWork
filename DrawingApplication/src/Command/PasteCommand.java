/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

/**
 *
 * @author Sterm
 */
import Shapes.Shape;
import Shapes.ShapeFactory;
import javafx.scene.layout.Pane;
import java.util.List;

import javafx.scene.layout.Pane;
import java.util.List;
import javafx.scene.paint.Color;





/**
 * Comando concreto per incollare una o più forme dalla clipboard nella scena.
 * Implementa il pattern Command.
 * Viene utilizzato per supportare azioni annullabili/rifacibili.
 */
public class PasteCommand implements Command {
    private Clipboard clipboard;
    private Pane drawingPane;
    private final List<Shape> drawShapes;
    private final double posX;
    private final double posY;
    
    /**
     * Costruttore del comando Paste.
     *
     * @param clipboard   contenitore temporaneo delle forme copiate
     * @param drawingPane pannello JavaFX su cui disegnare le nuove forme
     * @param drawShapes  lista delle forme attualmente disegnate
     * @param posX        coordinata X in cui incollare le nuove forme
     * @param posY        coordinata Y in cui incollare le nuove forme
     */
    public PasteCommand(Clipboard clipboard, Pane drawingPane, List<Shape> drawShapes, double posX, double posY) {
        this.clipboard = clipboard;
        this.drawingPane = drawingPane;
        this.drawShapes = drawShapes;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Esegue il comando di incolla.
     * Recupera le forme dalla clipboard, ne crea delle copie con le nuove coordinate
     * e le inserisce nel pane di disegno e nella lista di lavoro.
     */
    @Override
    public void execute() {
        if (!clipboard.isEmpty()) {
            List<Shape> clones = clipboard.getContents(); // cloni tramite Prototype
            for (Shape shape : clones) {
                // Calcola dimensioni originali
                double width = Math.abs(shape.getFinalX()  - shape.getInitialX());
                double height = Math.abs(shape.getFinalY() - shape.getInitialY());

                // Calcola nuove coordinate
                double newStartX = posX;
                double newStartY = posY;
                double newEndX = newStartX + width;
                double newEndY = newStartY + height;
                
                Color fillColor = shape.getType().equals("LINE")
                    ? Color.TRANSPARENT
                    : (Color) shape.getFXShape().getFill();
                // Ricrea la nuova shape
                Shape newShape = ShapeFactory.createShape(
                    shape.getType(),
                    newStartX, newStartY,
                    newEndX, newEndY,
                    Color.valueOf(shape.getFXShape().getStroke().toString()),
                    fillColor
                    //Color.valueOf(shape.getFXShape().getFill().toString())
                );

                javafx.scene.shape.Shape fxShape = newShape.toFXShape();
                newShape.setFXShape(fxShape);

                drawingPane.getChildren().add(fxShape);
                drawShapes.add(newShape);
            }
        }
    }
}

