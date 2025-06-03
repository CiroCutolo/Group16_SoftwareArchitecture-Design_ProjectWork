/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Shapes.Shape;

/**
 * Command for resizing a shape.
 * Uses the DrawingReceiver to perform the actual resizing.
 * 
 * @author gaetanof
 */
public class ResizeCommand implements Command {
    private final Shape shape;
    private final double oldWidth;
    private final double oldHeight;
    private final double newWidth;
    private final double newHeight;
    private final DrawingReceiver receiver;

    /**
     * Costruisce un comando di ridimensionamento per la forma specificata,
     * salvando le dimensioni vecchie e quelle nuove.
     *
     * @param shape la forma da ridimensionare
     * @param newWidth la nuova larghezza da impostare
     * @param newHeight la nuova altezza da impostare
     * @param receiver the DrawingReceiver to perform the actual resizing
     */
    public ResizeCommand(Shape shape, double newWidth, double newHeight, DrawingReceiver receiver) {
        this.shape = shape;
        this.oldWidth = shape.getWidth();
        this.oldHeight = shape.getHeight();
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.receiver = receiver;
    }

    /**
     * Esegue il comando di ridimensionamento applicando le nuove dimensioni
     * alla forma.
     */
    @Override
    public void execute() {
        receiver.resizeShape(shape, newWidth, newHeight);
    }

    /**
     * Annulla il comando di ridimensionamento, ripristinando le dimensioni
     * originali della forma.
     */
    @Override
    public void undo() {
        receiver.resizeShape(shape, oldWidth, oldHeight);
    }
}
