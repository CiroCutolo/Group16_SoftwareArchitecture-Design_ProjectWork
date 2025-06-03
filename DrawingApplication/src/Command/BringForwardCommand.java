/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sterm
 */
public class BringForwardCommand implements Command {
    private final Shape shape;
    private final List<Shape> drawShapes;
    private final Pane drawingPane;
    private int oldIndex;
    private boolean hasExecuted = false; 

    public BringForwardCommand(Shape shape, List<Shape> drawShapes, Pane drawingPane) {
        this.shape = shape;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        oldIndex = drawShapes.indexOf(shape);
        if (oldIndex < drawShapes.size() - 1) {
            Collections.swap(drawShapes, oldIndex, oldIndex + 1);
            redraw();
            hasExecuted = true;
        }
    }

    @Override
    public void undo() {
        if (!hasExecuted) return;
        
        int currentIndex = drawShapes.indexOf(shape);
        if (currentIndex > 0) {
            Collections.swap(drawShapes, currentIndex, currentIndex - 1);
            redraw();
        }
    }
    
    private void redraw() {
        drawingPane.getChildren().removeIf(node -> !(node instanceof Group));
        drawingPane.getChildren().addAll(
            drawShapes.stream().map(Shape::getFXShape).collect(Collectors.toList())
        );
    }
}

