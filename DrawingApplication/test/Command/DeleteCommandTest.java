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
public class DeleteCommandTest {
    private Pane drawingPane;
    private List<Shape> drawShapes;
    private Shape shape1;
    private Shape shape2;
    
    public DeleteCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /* Inizializza il toolkit JavaFX: indispensabile per usare Pane e Shape FX. */
        new JFXPanel();

        drawingPane = new Pane();
        drawShapes  = new ArrayList<>();

        /* Shape 1 */
        shape1 = new RectangleShape(0, 0, 10, 10);
        javafx.scene.shape.Shape fx1 = shape1.toFXShape();
        shape1.setFXShape(fx1);
        drawingPane.getChildren().add(fx1);
        drawShapes.add(shape1);

        /* Shape 2 – sarà quella da eliminare. */
        shape2 = new RectangleShape(20, 20, 30, 30);
        javafx.scene.shape.Shape fx2 = shape2.toFXShape();
        shape2.setFXShape(fx2);
        drawingPane.getChildren().add(fx2);
        drawShapes.add(shape2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class DeleteCommand.
     */
    @Test
    public void testExecute_RemovesShape() {
        DeleteCommand command = new DeleteCommand(drawingPane, drawShapes, shape2);
        command.execute();

        assertFalse(drawShapes.contains(shape2));
        assertEquals(1, drawShapes.size());

        assertFalse(drawingPane.getChildren().contains(shape2.getFXShape()));
        assertEquals(1, drawingPane.getChildren().size());
    }

    /**
     * Verifica che undo() ripristini correttamente la shape eliminata.
     */
    @Test
    public void testUndo_RestoresShape() {
        DeleteCommand command = new DeleteCommand(drawingPane, drawShapes, shape2);
        command.execute();  // prima la eliminiamo
        command.undo();     // poi annulliamo

        assertTrue(drawShapes.contains(shape2));
        assertEquals(2, drawShapes.size());

        assertTrue(drawingPane.getChildren().contains(shape2.getFXShape()));
        assertEquals(2, drawingPane.getChildren().size());
    }
    
    /**
     * Verifica la robustezza: se la shape da eliminare è null non deve accadere nulla.
     */
    @Test
    public void testExecute_NullShapeToDelete() {
        DeleteCommand command = new DeleteCommand(drawingPane, drawShapes, null);
        command.execute();

        /* Collezioni intatte */
        assertEquals(2, drawShapes.size());
        assertEquals(2, drawingPane.getChildren().size());
    }
}
