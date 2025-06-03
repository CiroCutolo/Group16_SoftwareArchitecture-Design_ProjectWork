/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Comando per inviare una forma di un livello in avanti nel disegno.
 * Usa il DrawingReceiver per eseguire l'operazione di manipolazione dei livelli.
 *
 * @author Sterm
 */
public class SendBackwardCommand implements Command {
    private final Shape shape;
    private final DrawingReceiver receiver;

    public SendBackwardCommand(Shape shape, DrawingReceiver receiver) {
        this.shape = shape;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.sendBackward(shape);
    }

    @Override
    public void undo() {
        receiver.bringForward(shape);
    }
}

