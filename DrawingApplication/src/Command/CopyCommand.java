/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;
import Shapes.Shape;
import java.util.List;
/**
 *
 * @author Sterm
 */



/**
 * Comando concreto per copiare una lista di forme nella clipboard.
 * Implementa il pattern Command.
 * Viene utilizzato per supportare operazioni annullabili/rifacibili.
 */
public class CopyCommand implements Command {
    private List<Shape> selection;
    private Clipboard clipboard;
    
    /**
     * Costruttore del comando Copy.
     *
     * @param selection lista di forme selezionate da copiare
     * @param clipboard oggetto clipboard in cui salvare le forme copiate
     */
    public CopyCommand(List<Shape> selection, Clipboard clipboard) {
        this.selection = selection;
        this.clipboard = clipboard;
    }

    /**
     * Esegue il comando di copia.
     * Inserisce nella clipboard la lista delle forme selezionate,
     * sfruttando la logica di clonazione interna della clipboard.
     */
    @Override
    public void execute() {
        clipboard.setContents(selection);
    }
    
    @Override
    public void undo(){
        
    }
}
