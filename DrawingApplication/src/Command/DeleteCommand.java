/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Command for deleting a shape from the drawing.
 * Uses the DrawingReceiver to perform the actual deletion.
 * 
 * @author ciroc
 */
public class DeleteCommand implements Command {
    private final DrawingReceiver receiver;
    private final Shape shapeToDelete;
    
    public DeleteCommand(DrawingReceiver receiver, Shape shapeToDelete) {
        this.receiver = receiver;
        this.shapeToDelete = shapeToDelete;
    }

    @Override
    public void execute() {
        if (shapeToDelete != null) {
            receiver.removeShape(shapeToDelete);
        }
    }

    @Override
    public void undo() {
        if (shapeToDelete != null) {
            receiver.insertShape(shapeToDelete);
        }
    }
}
