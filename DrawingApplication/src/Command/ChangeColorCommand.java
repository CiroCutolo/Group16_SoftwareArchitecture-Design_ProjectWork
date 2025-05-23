/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import javafx.scene.paint.Color;

/**
 *
 * @author Sterm
 */
public class ChangeColorCommand implements Command{

    private final Shape shape;
    private final Color newStroke, newFill;
    private final Color oldStroke, oldFill;

    public ChangeColorCommand(Shape shape, Color newStroke, Color newFill) {
        this.shape = shape;
        this.newStroke = newStroke;
        this.newFill = newFill;
        this.oldStroke = shape.getPerimetralColor();
        this.oldFill = shape.getInternalColor();
    }

    @Override
    public void execute() {
        shape.setPerimetralColor(newStroke);
        shape.setInternalColor(newFill);
        updateFXShape();
    }


    @Override
    public void undo() {
        shape.setPerimetralColor(oldStroke);
        shape.setInternalColor(oldFill);
        updateFXShape();
    }


    private void updateFXShape() {
        javafx.scene.shape.Shape fx = shape.getFXShape();
        fx.setStroke(shape.getPerimetralColor());
        fx.setFill(shape.getInternalColor());
}


}
