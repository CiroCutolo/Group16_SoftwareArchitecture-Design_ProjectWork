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
public class CopyCommandTest {
    
    public CopyCommandTest() {
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
     * Verifica che il comando CopyCommand copi correttamente una forma nella clipboard.
     */
    @Test
    public void testExecute_SingleShape() {
        Clipboard clipboard = new Clipboard();
        List<Shape> selection = new ArrayList<>();
        selection.add(new RectangleShape(0, 0, 10, 10));

        CopyCommand copyCommand = new CopyCommand(selection, clipboard);
        copyCommand.execute();

        List<Shape> contents = clipboard.getContents();
        assertEquals(1, contents.size());
        assertEquals("RECTANGLE", contents.get(0).getType());
        assertNotSame(selection.get(0), contents.get(0)); // deve essere un clone
    }

        /**
     * Verifica che CopyCommand non fallisca con una lista vuota.
     */
    @Test
    public void testExecute_EmptySelection() {
        Clipboard clipboard = new Clipboard();
        List<Shape> selection = new ArrayList<>();

        CopyCommand copyCommand = new CopyCommand(selection, clipboard);
        copyCommand.execute();

        List<Shape> contents = clipboard.getContents();
        assertTrue(contents.isEmpty());
    }
    
        /**
     * Verifica che CopyCommand copi più forme correttamente.
     */
    @Test
    public void testExecute_MultipleShapes() {
        Clipboard clipboard = new Clipboard();
        List<Shape> selection = new ArrayList<>();
        selection.add(new RectangleShape(0, 0, 10, 10));
        selection.add(new EllipseShape(10, 10, 20, 20));

        CopyCommand copyCommand = new CopyCommand(selection, clipboard);
        copyCommand.execute();

        List<Shape> contents = clipboard.getContents();
        assertEquals(2, contents.size());
        assertEquals("RECTANGLE", contents.get(0).getType());
        assertEquals("ELLIPSE", contents.get(1).getType());
    }


}
