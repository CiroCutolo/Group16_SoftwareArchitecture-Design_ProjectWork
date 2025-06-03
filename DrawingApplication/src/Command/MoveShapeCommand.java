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
    private final double dx, dy;

    public MoveShapeCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        shape.moveBy(dx, dy);
    }

    @Override
    public void undo() {
        shape.moveBy(-dx, -dy);
    }
}
