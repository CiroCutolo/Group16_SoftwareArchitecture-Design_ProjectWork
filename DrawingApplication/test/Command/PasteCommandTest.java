/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.EllipseShape;
import Shapes.RectangleShape;
import Shapes.Shape;
import java.util.ArrayList;
import java.util.List;
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
public class PasteCommandTest {
    
    public PasteCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

        /**
     * Verifica che il comando incolli correttamente una singola shape dalla clipboard.
     */
    @Test
    public void testExecute_PastesSingleShape() {
        Clipboard clipboard = new Clipboard();
        List<Shape> original = new ArrayList<>();
        original.add(new RectangleShape(0, 0, 10, 10));
        clipboard.setContents(original);

        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        PasteCommand paste = new PasteCommand(clipboard, pane, drawShapes, 5, 5);
        paste.execute();

        assertEquals(1, drawShapes.size());
        assertEquals("RECTANGLE", drawShapes.get(0).getType());
        assertEquals(1, pane.getChildren().size());
    }
    
        /**
     * Verifica che il comando non modifichi nulla se la clipboard è vuota.
     */
    @Test
    public void testExecute_WithEmptyClipboard() {
        Clipboard clipboard = new Clipboard(); // clipboard vuota
        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        PasteCommand paste = new PasteCommand(clipboard, pane, drawShapes, 0, 0);
        paste.execute();

        assertTrue(drawShapes.isEmpty());
        assertTrue(pane.getChildren().isEmpty());
    }
    
        /**
     * Verifica che una shape venga incollata correttamente anche con un offset positivo.
     */
    @Test
    public void testExecute_PastesShapeAtDifferentOffset() {
        Clipboard clipboard = new Clipboard();
        List<Shape> copied = new ArrayList<>();
        copied.add(new EllipseShape(10, 10, 20, 20)); // forma base
        clipboard.setContents(copied);

        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        PasteCommand paste = new PasteCommand(clipboard, pane, drawShapes, 50, 50); // offset
        paste.execute();

        assertEquals(1, drawShapes.size());
        assertEquals("ELLIPSE", drawShapes.get(0).getType());
        assertEquals(1, pane.getChildren().size());
    }
    
        /**
     * Verifica che la forma incollata sia una nuova istanza (clone) indipendente dall’originale.
     */
    @Test
    public void testExecute_PastedShapeIsCloned() {
        Clipboard clipboard = new Clipboard();
        RectangleShape original = new RectangleShape(0, 0, 20, 20);
        List<Shape> copied = new ArrayList<>();
        copied.add(original);
        clipboard.setContents(copied);

        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        PasteCommand paste = new PasteCommand(clipboard, pane, drawShapes, 0, 0);
        paste.execute();

        Shape pasted = drawShapes.get(0);
        assertNotSame(original, pasted); // devono essere oggetti diversi
        assertEquals("RECTANGLE", pasted.getType());
    }


    
}
