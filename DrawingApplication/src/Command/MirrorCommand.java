/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;

/**
 * Comando per specchiatura orizzontale o verticale di una forma.
 * Usa il DrawingReceiver per eseguire l'operazione di mirroring.
 *
 * @author genna
 */
public class MirrorCommand implements Command {
    private final Shape shape;
    private final boolean horizontal;
    private final DrawingReceiver receiver;

    public MirrorCommand(Shape shape, boolean horizontal, DrawingReceiver receiver) {
        this.shape = shape;
        this.horizontal = horizontal;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.mirrorShape(shape, horizontal);
    }

    @Override
    public void undo() {
        receiver.mirrorShape(shape, horizontal); // Specchiatura due volte ritorna allo stato originale
    }
}
