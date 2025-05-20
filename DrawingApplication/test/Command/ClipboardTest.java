/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.EllipseShape;
import Shapes.LineShape;
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
public class ClipboardTest {
    
    public ClipboardTest() {
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
    * Verifica che la clipboard cloni correttamente una singola shape.
    * Controlla che la forma recuperata sia:
    * - della stessa dimensione e tipo
    * - una copia indipendente dall’originale (clone)
    */
    @Test
    public void testSetAndGetContents_SingleShape() {
        Clipboard clipboard = new Clipboard();
        List<Shape> originalList = new ArrayList<>();
        originalList.add(new RectangleShape(0, 0, 10, 10));

        clipboard.setContents(originalList);
        List<Shape> retrieved = clipboard.getContents();

        assertEquals(1, retrieved.size());
        assertNotSame(originalList.get(0), retrieved.get(0)); // clone verificato
        assertEquals("RECTANGLE", retrieved.get(0).getType());
    }
    
    /**
    * Verifica che ogni chiamata a getContents() restituisca un clone indipendente.
    * Serve a garantire che la clipboard non restituisca riferimenti condivisi nel tempo.
    */
        @Test
    public void testGetContents_ReturnsClonedList() {
        Clipboard clipboard = new Clipboard();

        List<Shape> shapes = new ArrayList<>();
        shapes.add(new LineShape(1, 1, 2, 2));

        clipboard.setContents(shapes);

        List<Shape> retrieved1 = clipboard.getContents();
        List<Shape> retrieved2 = clipboard.getContents();

        assertNotSame(retrieved1, retrieved2); // liste diverse
        assertNotSame(retrieved1.get(0), retrieved2.get(0)); // oggetti clonati diversi
    }

        /**
     * Verifica che una LineShape venga clonata correttamente nella clipboard.
     * Controlla tipo, dimensione e che l'oggetto restituito sia un'istanza indipendente.
     */
    @Test
    public void testSetAndGetContents_LineShape() {
        Clipboard clipboard = new Clipboard();
        List<Shape> originalList = new ArrayList<>();
        originalList.add(new LineShape(0, 0, 100, 100));

        clipboard.setContents(originalList);
        List<Shape> retrieved = clipboard.getContents();

        assertEquals(1, retrieved.size());
        assertNotSame(originalList.get(0), retrieved.get(0));
        assertEquals("LINE", retrieved.get(0).getType());
    }
    
        /**
     * Verifica che una RectangleShape venga clonata correttamente.
     */
    @Test
    public void testSetAndGetContents_RectangleShape_NoColors() {
        Clipboard clipboard = new Clipboard();
        List<Shape> originalList = new ArrayList<>();

        RectangleShape rect = new RectangleShape(10, 10, 50, 50); 
        originalList.add(rect);

        clipboard.setContents(originalList);
        List<Shape> retrieved = clipboard.getContents();

        assertEquals(1, retrieved.size());
        assertNotSame(originalList.get(0), retrieved.get(0));
        assertEquals("RECTANGLE", retrieved.get(0).getType());
    }

        /**
     * Verifica che una EllipseShape venga clonata correttamente.
     */
    @Test
    public void testSetAndGetContents_EllipseShape_NoColors() {
        Clipboard clipboard = new Clipboard();
        List<Shape> originalList = new ArrayList<>();

        EllipseShape ellipse = new EllipseShape(50, 50, 100, 100); 
        originalList.add(ellipse);

        clipboard.setContents(originalList);
        List<Shape> retrieved = clipboard.getContents();

        assertEquals(1, retrieved.size());
        assertNotSame(originalList.get(0), retrieved.get(0));
        assertEquals("ELLIPSE", retrieved.get(0).getType());
    }


}
