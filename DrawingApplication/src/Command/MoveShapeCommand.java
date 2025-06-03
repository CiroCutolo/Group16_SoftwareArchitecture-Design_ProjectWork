/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 *
 * @author genna
 */
/**
 * Incapsula lo spostamento (dx, dy) di una Shape.
 */
public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final double dx;
    private final double dy;
    private final DrawingReceiver receiver;

    public MoveShapeCommand(Shape shape, double dx, double dy, DrawingReceiver receiver) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.moveShape(shape, dx, dy);
    }

    @Override
    public void undo() {
        receiver.moveShape(shape, -dx, -dy);
    }
}
