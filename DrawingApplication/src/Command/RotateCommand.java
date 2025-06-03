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
public class RotateCommand implements Command {

    private final Shape shape;
    private final double angle;

    public RotateCommand(Shape shape, double angle) {
        this.shape = shape;
        this.angle = angle;
    }

    @Override public void execute() { shape.rotate(angle); }
    @Override public void undo()    { shape.rotate(-angle); }
}
