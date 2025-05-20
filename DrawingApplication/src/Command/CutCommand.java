/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Shapes.Shape;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author gaetanof
 */

/**  
* Comando concreto per tagliare una o più forme selezionate dalla scena e copiarle nella clipboard.
* Implementa il pattern Command. 
* Viene utilizzato per supportare operazioni annullabili/rifacibili e per separare invocazione da esecuzione.
*/
public class CutCommand implements Command{
    private List<Shape> selection;
    private Clipboard clipboard;
    private List<Shape> drawShapes;
    private Pane drawingPane;
    
    /** 
    * Costruttore del comando Cut.
    * @param selection lista di forme attualmente selezionate
    * @param clipboard contenitore temporaneo delle forme copiate 
    * @param drawShapes lista globale delle forme disegnate 
    * @param drawingPane pannello JavaFX da cui rimuovere le forme tagliate
    */
    public CutCommand(List<Shape> selection, Clipboard clipboard, List<Shape> drawShapes, Pane drawingPane) {
        this.selection = selection;
        this.clipboard = clipboard;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }
    
    /**
    * Esegue il comando di taglio. 
    * Copia le forme selezionate nella clipboard e le rimuove dal pane di disegno e dalla lista interna.
    */
    @Override
    public void execute() {
        if (selection == null || selection.isEmpty()) return;

        // Copy to clipboard
        clipboard.setContents(selection);

        // Remove from pane and internal list
        for (Shape shape : selection) {
            drawingPane.getChildren().remove(shape.getFXShape());
            drawShapes.remove(shape);
        }
    }
    
}
