/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import Shapes.Shape;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author ciroc
 */
public class DeleteCommand implements Command{
    private Pane drawingPane;
    private List<Shape> drawShapes;
    private Shape shapeToDelete;
    
    public DeleteCommand(Pane drawingPane, List<Shape> drawShapes, Shape shapeToDelete){
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
        this.shapeToDelete = shapeToDelete;
    }

    @Override
    public void execute() {
        if(shapeToDelete != null){
            drawingPane.getChildren().remove(shapeToDelete.getFXShape());
            drawShapes.remove(shapeToDelete);
        }
    }

    @Override
    public void undo() {
        if (shapeToDelete != null) {
            javafx.scene.shape.Shape fxShape = shapeToDelete.toFXShape();
            shapeToDelete.setFXShape(fxShape);
            drawingPane.getChildren().add(fxShape);
            drawShapes.add(shapeToDelete);
        }
    }
    
}
