/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import javafx.geometry.Bounds;
import javafx.scene.transform.Scale;

/**
 *
 * @author genna
 */
public class MirrorCommand implements Command {
    private final Shape shape;
    private final boolean horizontal;
    private Scale scaleTransform;

    public MirrorCommand(Shape shape, boolean horizontal) {
        this.shape = shape;
        this.horizontal = horizontal;
    }

    @Override
    public void execute() {
        javafx.scene.shape.Shape fxShape = shape.getFXShape();
        Bounds b = fxShape.getBoundsInLocal();               // usa i bound locali
        double pivotX = (b.getMinX() + b.getMaxX()) / 2.0;
        double pivotY = (b.getMinY() + b.getMaxY()) / 2.0;

        double sx = horizontal ? -1.0 : 1.0;
        double sy = horizontal ?  1.0 : -1.0;

        scaleTransform = new Scale(sx, sy, pivotX, pivotY);
        fxShape.getTransforms().add(scaleTransform);
    }

    @Override
    public void undo() {
        shape.getFXShape().getTransforms().remove(scaleTransform);
    }
}