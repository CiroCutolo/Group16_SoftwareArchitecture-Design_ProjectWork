/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;
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
    private boolean hasExecuted = false;

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
        hasExecuted = true;
    }


    @Override
    public void undo() {
        if(!hasExecuted) return;
        
        drawShapes.remove(shape);
        drawShapes.add(oldIndex, shape);
        redraw();
    }


    private void redraw() {
        drawingPane.getChildren().removeIf(node -> !(node instanceof Group));
        drawingPane.getChildren().addAll(
            drawShapes.stream().map(Shape::getFXShape).collect(Collectors.toList())
        );
    }
}
