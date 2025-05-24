/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.RectangleShape;
import Shapes.Shape;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ciroc
 */
public class SendToBackCommandTest {
    
    private List<Shape> drawShapes;
    private Pane drawingPane;
    private Shape shape1;
    private Shape shape2;
    private Shape shape3;

    @Before
    public void setUp() {
        new JFXPanel(); 

        drawingPane = new Pane();

        shape1 = new RectangleShape(0, 0, 10, 10);
        shape2 = new RectangleShape(10, 10, 20, 20);
        shape3 = new RectangleShape(20, 20, 30, 30);
        
        javafx.scene.shape.Shape fxShape1 = shape1.toFXShape();
        shape1.setFXShape(fxShape1);
        javafx.scene.shape.Shape fxShape2 = shape2.toFXShape();
        shape2.setFXShape(fxShape2);
        javafx.scene.shape.Shape fxShape3 = shape3.toFXShape();
        shape3.setFXShape(fxShape3);
        
        drawShapes = new ArrayList<>();
        drawShapes.add(shape1);
        drawShapes.add(shape2);
        drawShapes.add(shape3);

        drawingPane.getChildren().addAll(shape1.getFXShape(), shape2.getFXShape(), shape3.getFXShape());
    }

    @Test
    public void testExecute_AndUndo() {
        SendToBackCommand command = new SendToBackCommand(shape2, drawShapes, drawingPane);

        assertSame(1, drawShapes.indexOf(shape2));

        command.execute();
        assertSame(0, drawShapes.indexOf(shape2));

        command.undo();      
        assertSame(1, drawShapes.indexOf(shape2));
    }

    @Test
    public void testUndo_WithoutPreviousExecute() {
        SendToBackCommand command = new SendToBackCommand(shape2, drawShapes, drawingPane);

        command.undo();

        assertEquals(1, drawShapes.indexOf(shape2));
    }
    
}
