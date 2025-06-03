/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Shapes.Shape;

/**
 * Comando per eseguire il ridimensionamento di una forma.
 * 
 * Implementa l'interfaccia {@link Command} per supportare le operazioni di
 * esecuzione e annullamento (undo) del ridimensionamento. Memorizza le
 * dimensioni precedenti e quelle nuove, in modo da poter eseguire e annullare
 * correttamente l'operazione.
 * 
 *
 * @author gaetanof
 */
public class ResizeCommand implements Command {

    /**
     * La forma da ridimensionare
     */
    private final Shape shape;

    /**
     * Larghezza originale della forma prima del ridimensionamento
     */
    private final double oldWidth;

    /**
     * Altezza originale della forma prima del ridimensionamento
     */
    private final double oldHeight;

    /**
     * Nuova larghezza da applicare alla forma
     */
    private final double newWidth;

    /**
     * Nuova altezza da applicare alla forma
     */
    private final double newHeight;

    /**
     * Costruisce un comando di ridimensionamento per la forma specificata,
     * salvando le dimensioni vecchie e quelle nuove.
     *
     * @param shape la forma da ridimensionare
     * @param newWidth la nuova larghezza da impostare
     * @param newHeight la nuova altezza da impostare
     */
    public ResizeCommand(Shape shape, double newWidth, double newHeight) {
        this.shape = shape;
        this.oldWidth = shape.getWidth();
        this.oldHeight = shape.getHeight();
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    /**
     * Esegue il comando di ridimensionamento applicando le nuove dimensioni
     * alla forma.
     */
    @Override
    public void execute() {
        shape.resize(newWidth, newHeight);
    }

    /**
     * Annulla il comando di ridimensionamento, ripristinando le dimensioni
     * originali della forma.
     */
    @Override
    public void undo() {
        shape.resize(oldWidth, oldHeight);
    }
}
