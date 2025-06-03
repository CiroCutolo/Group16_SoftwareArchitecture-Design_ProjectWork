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
public class InsertShapeCommand implements Command{
    private final Shape shape;
    private final List<Shape> drawShapes;
    private final Pane drawingPane;

    public InsertShapeCommand(Shape shape, List<Shape> drawShapes, Pane drawingPane) {
        this.shape = shape;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        javafx.scene.shape.Shape fxShape = shape.toFXShape();
        shape.setFXShape(fxShape);
        drawShapes.add(shape);
        drawingPane.getChildren().add(fxShape);
    }


    @Override
    public void undo() {
        drawShapes.remove(shape);
        drawingPane.getChildren().remove(shape.getFXShape());
    }

}
