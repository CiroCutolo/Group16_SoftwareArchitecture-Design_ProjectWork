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
public class MirrorCommand implements Command {

    private final Shape shape;
    private final boolean horizontal;

    public MirrorCommand(Shape shape, boolean horizontal) {
        this.shape = shape;
        this.horizontal = horizontal;
    }

    @Override public void execute() { shape.mirror(horizontal); }
    @Override public void undo()    { shape.mirror(horizontal); }
}
