/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Comando per inviare una forma all'ultimo livello.
 * Usa il DrawingReceiver per eseguire l'operazione di layer manipulation.
 *
 * @author Sterm
 */
public class SendToBackCommand implements Command {
    private final Shape shape;
    private final DrawingReceiver receiver;

    public SendToBackCommand(Shape shape, DrawingReceiver receiver) {
        this.shape = shape;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.sendToBack(shape);
    }

    @Override
    public void undo() {
        receiver.bringToFront(shape);
    }
}

