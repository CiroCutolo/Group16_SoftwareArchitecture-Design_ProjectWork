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
public class SendToBackCommand implements Command {
    private final Shape shape;
    private final List<Shape> drawShapes;
    private final Pane drawingPane;
    private int oldIndex;
    private boolean hasExecuted = false;

    public SendToBackCommand(Shape shape, List<Shape> drawShapes, Pane drawingPane) {
        this.shape = shape;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        oldIndex = drawShapes.indexOf(shape);
        drawShapes.remove(shape);
        drawShapes.add(0, shape); // in fondo (primo nel rendering)
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

