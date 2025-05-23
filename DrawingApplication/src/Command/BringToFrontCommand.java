/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sterm
 */
public class BringToFrontCommand implements Command {
    private final Shape shape;
    private final List<Shape> drawShapes;
    private final Pane drawingPane;
    private int oldIndex;

    public BringToFrontCommand(Shape shape, List<Shape> drawShapes, Pane drawingPane) {
        this.shape = shape;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        oldIndex = drawShapes.indexOf(shape);
        drawShapes.remove(shape);
        drawShapes.add(shape); // ora è in fondo alla lista (quindi in cima nel rendering)
        redraw();
    }


    @Override
    public void undo() {
        drawShapes.remove(shape);
        drawShapes.add(oldIndex, shape);
        redraw();
    }


    private void redraw() {
        drawingPane.getChildren().clear();
        for (Shape s : drawShapes) {
            drawingPane.getChildren().add(s.getFXShape());
        }
    }
}
