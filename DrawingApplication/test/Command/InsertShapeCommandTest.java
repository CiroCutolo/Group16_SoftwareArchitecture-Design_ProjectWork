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
 * @author genna
 */
public class InsertShapeCommandTest {
    private Pane drawingPane;
    private List<Shape> drawShapes;
    private Shape shapeToInsert;
    
    public InsertShapeCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         /* Obbligatorio per usare i controlli JavaFX nei test headless. */
        new JFXPanel();

        drawingPane = new Pane();
        drawShapes  = new ArrayList<>();

        /* La forma da inserire NON è ancora nel pane né nella lista. */
        shapeToInsert = new RectangleShape(5, 5, 15, 15);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Verifica che execute() aggiunga correttamente la shape.
     */
    @Test
    public void testExecute_AddsShape() {
        InsertShapeCommand command = new InsertShapeCommand(shapeToInsert, drawShapes, drawingPane);
        command.execute();

        assertTrue(drawShapes.contains(shapeToInsert));
        assertEquals(1, drawShapes.size());

        assertTrue(drawingPane.getChildren().contains(shapeToInsert.getFXShape()));
        assertEquals(1, drawingPane.getChildren().size());
    }

    /**
     * Verifica che undo() rimuova la shape precedentemente inserita.
     */
    @Test
    public void testUndo_RemovesShape() {
        InsertShapeCommand command = new InsertShapeCommand(shapeToInsert, drawShapes, drawingPane);
        command.execute();  // inserisce
        command.undo();     // rimuove

        assertFalse(drawShapes.contains(shapeToInsert));
        assertEquals(0, drawShapes.size());

        assertFalse(drawingPane.getChildren().contains(shapeToInsert.getFXShape()));
        assertEquals(0, drawingPane.getChildren().size());
    }

    /**
     * Se la shape è null ci si aspetta una NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testExecute_NullShape_ThrowsException() {
        InsertShapeCommand command = new InsertShapeCommand(null, drawShapes, drawingPane);
        command.execute();
    }
    
}
