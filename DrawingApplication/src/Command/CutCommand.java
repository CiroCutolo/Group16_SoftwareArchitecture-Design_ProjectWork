/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Shapes.Shape;

/**
* Command for cutting a shape from the drawing and copying it to the clipboard.
* Uses the DrawingReceiver to perform the actual cut operation.
 *
 * @author gaetanof
 */
public class CutCommand implements Command {
    private final Shape shape;
    private final Clipboard clipboard;
    private final DrawingReceiver receiver;
    private Shape shapeClone;
    
    /** 
    * Constructor for the cut command.
    * @param shape the shape to cut
    * @param clipboard clipboard to store the cut shape
    * @param receiver the drawing receiver that will handle the cut operation
    */
    public CutCommand(Shape shape, Clipboard clipboard, DrawingReceiver receiver) {
        this.shape = shape;
        this.clipboard = clipboard;
        this.receiver = receiver;
        this.shapeClone = shape.clone();
    }
    
    @Override
    public void execute() {
        if (shape != null) {
            receiver.cutShape(shape, clipboard);
        }
    }
    
    @Override
    public void undo() {
        if (shapeClone != null) {
            receiver.insertShape(shapeClone);
        }
    }
}
