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
public class CutCommand implements Command{
    private List<Shape> selection;
    private Clipboard clipboard;
    private List<Shape> drawShapes;
    private Pane drawingPane;
    
    public CutCommand(List<Shape> selection, Clipboard clipboard, List<Shape> drawShapes, Pane drawingPane) {
        this.selection = selection;
        this.clipboard = clipboard;
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }
    
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
