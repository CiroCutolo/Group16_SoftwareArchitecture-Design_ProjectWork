/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Comando per ruotare una forma di un angolo specificato.
 * Usa il DrawingReceiver per eseguire l'operazione di rotazione.
 *
 * @author genna
 */
public class RotateCommand implements Command {
    private final Shape shape;
    private final double angle;
    private final DrawingReceiver receiver;

    public RotateCommand(Shape shape, double angle, DrawingReceiver receiver) {
        this.shape = shape;
        this.angle = angle;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.rotateShape(shape, angle);
    }

    @Override
    public void undo() {
        receiver.rotateShape(shape, -angle);
    }
}
