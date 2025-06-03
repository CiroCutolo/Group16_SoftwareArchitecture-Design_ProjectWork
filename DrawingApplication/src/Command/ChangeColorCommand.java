/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import javafx.scene.paint.Color;

/**
 * Command for changing the color of a shape.
 * Uses the DrawingReceiver to perform the actual color change.
 *
 * @author Sterm
 */
public class ChangeColorCommand implements Command {
    private final Shape shape;
    private final Color newStroke, newFill;
    private final Color oldStroke, oldFill;
    private final DrawingReceiver receiver;

    public ChangeColorCommand(Shape shape, Color newStroke, Color newFill, DrawingReceiver receiver) {
        this.shape = shape;
        this.newStroke = newStroke;
        this.newFill = newFill;
        this.oldStroke = shape.getPerimetralColor();
        this.oldFill = shape.getInternalColor();
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.changeShapeColor(shape, newStroke, newFill);
    }

    @Override
    public void undo() {
        receiver.changeShapeColor(shape, oldStroke, oldFill);
    }
}
