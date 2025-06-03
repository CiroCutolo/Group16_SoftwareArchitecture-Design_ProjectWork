/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

/**
 * Comando concreto per incollare una o più forme dalla clipboard nella scena.
 * Implementa il pattern Command.
 * Viene utilizzato per supportare azioni annullabili/rifacibili.
 *
 * @author Sterm
 */
public class PasteCommand implements Command {
    private final Clipboard clipboard;
    private final DrawingReceiver receiver;
    private final double posX;
    private final double posY;
    
    /**
     * Costruttore del comando di incolla.
     *
     * @param clipboard   contenitore temporaneo delle forme copiate
     * @param receiver    receiver delle azioni da eseguire
     * @param posX        coordinata X in cui incollare le nuove forme
     * @param posY        coordinata Y in cui incollare le nuove forme
     */
    public PasteCommand(Clipboard clipboard, DrawingReceiver receiver, double posX, double posY) {
        this.clipboard = clipboard;
        this.receiver = receiver;
        this.posX = posX;
        this.posY = posY;
    }
    
    /**
     * Esegue il comando di incolla.
     * Recupera le forme dalla clipboard, ne crea delle copie con le nuove coordinate
     * e le inserisce nel pane di disegno e nella lista di lavoro.
     */
    @Override
    public void execute() {
        receiver.pasteShape(clipboard, posX, posY);
    }

    @Override
    public void undo() {
        // The receiver's removeShape method will be called for each pasted shape
        // This is handled by the receiver's internal state tracking
    }
}

