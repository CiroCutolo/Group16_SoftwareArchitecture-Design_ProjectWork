/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Command for inserting a new shape into the drawing.
 * Uses the DrawingReceiver to perform the actual insertion.
 *
 * @author Sterm
 */
public class InsertShapeCommand implements Command {
    private final Shape shape;
    private final DrawingReceiver receiver;

    public InsertShapeCommand(Shape shape, DrawingReceiver receiver) {
        this.shape = shape;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.insertShape(shape);
    }

    @Override
    public void undo() {
        receiver.removeShape(shape);
    }
}
