/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**

 * Comando per portare una forma in primo piano.
 * Usa il DrawingReceiver per eseguire l'operazione di layer manipulation.
 * 
 * @author Sterm
 */
public class BringToFrontCommand implements Command {
    private final Shape shape;
    private final DrawingReceiver receiver;


    public BringToFrontCommand(Shape shape, DrawingReceiver receiver) {
        this.shape = shape;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.bringToFront(shape);

    }

    @Override
    public void undo() {
        receiver.sendToBack(shape);
    }
}
