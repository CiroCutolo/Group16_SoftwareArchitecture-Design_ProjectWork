/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;


/**
 *
 * @author Sterm
 */
public class BringForwardCommand implements Command {
    private final Shape shape;
    private final DrawingReceiver receiver;

    public BringForwardCommand(Shape shape, DrawingReceiver receiver) {
        this.shape = shape;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.bringForward(shape);
    }

    @Override
    public void undo() {
        receiver.sendBackward(shape);
    }
    
    //private void redraw() {
    //    drawingPane.getChildren().removeIf(node -> !(node instanceof Group));
    //    drawingPane.getChildren().addAll(
    //        drawShapes.stream().map(Shape::getFXShape).collect(Collectors.toList())
    //    );
    //}
}

