/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Shapes;

import javafx.scene.paint.Color;
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
public class ShapeFactoryTest {
    
    public ShapeFactoryTest() {
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
     * Test of createShape method, of class ShapeFactory.
     */
    @Test
    public void testCreateShape_5args() {
        System.out.println("createShape con 5 argomenti");

        String selected_shape = "RECTANGLE";  // MAIUSCOLO
        double x1 = 10.0;
        double y1 = 20.0;
        double x2 = 30.0;
        double y2 = 40.0;

        Shape result = ShapeFactory.createShape(selected_shape, x1, y1, x2, y2);

        assertNotNull("La forma non deve essere null", result);
        assertTrue("La forma deve essere un RectangleShape", result instanceof RectangleShape);
    }
    
        /**
     * Test: crea rettangolo corretto.
     */
    @Test
    public void testCreateRectangle() {
        Shape shape = ShapeFactory.createShape("RECTANGLE", 10, 20, 30, 40);
        assertTrue(shape instanceof RectangleShape);
        assertEquals("RECTANGLE", shape.getType());
    }

    /**
     * Test: crea linea corretta.
     */
    @Test
    public void testCreateLine() {
        Shape shape = ShapeFactory.createShape("LINE", 0, 0, 100, 100);
        assertEquals("LINE", shape.getType());
    }

    /**
     * Test: crea ellisse corretta.
     */
    @Test
    public void testCreateEllipse() {
        Shape shape = ShapeFactory.createShape("ELLIPSE", 5, 5, 15, 25);
        assertEquals("ELLIPSE", shape.getType());
    }

    /**
     * Test: stringa tipo forma non valida.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateShape_InvalidType() {
        ShapeFactory.createShape("TRIANGLE", 0, 0, 1, 1);
    }


    @Test
    public void testCreateShape_7args() {
        System.out.println("createShape con 7 argomenti");

        String selected_shape = "ELLIPSE";  // MAIUSCOLO
        double x1 = 5.0;
        double y1 = 5.0;
        double x2 = 25.0;
        double y2 = 25.0;
        Color strokeColor = Color.BLACK;
        Color fillColor = Color.RED;

        Shape result = ShapeFactory.createShape(selected_shape, x1, y1, x2, y2, strokeColor, fillColor);

        assertNotNull("La forma non deve essere null", result);
        assertTrue("La forma deve essere un EllipseShape", result instanceof EllipseShape);
    }
    
        /**
     * Test: rettangolo con colori validi.
     */
    @Test
    public void testCreateRectangleWithColors() {
        Shape shape = ShapeFactory.createShape("RECTANGLE", 0, 0, 20, 20, Color.BLACK, Color.WHITE);
        assertTrue(shape instanceof RectangleShape);
        assertEquals("RECTANGLE", shape.getType());
        assertEquals("0x000000ff", Color.valueOf(shape.perimetralColorString).toString());
        assertEquals("0xffffffff", Color.valueOf(shape.internalColorString).toString());
    }

    /**
     * Test: ellisse con colori distinti.
     */
    @Test
    public void testCreateEllipseWithColors() {
        Shape shape = ShapeFactory.createShape("ELLIPSE", 1, 2, 3, 4, Color.BLUE, Color.YELLOW);
        assertEquals("ELLIPSE", shape.getType());
        assertEquals("0x0000ffff", Color.valueOf(shape.perimetralColorString).toString());
        assertEquals("0xffff00ff", Color.valueOf(shape.internalColorString).toString());
    }


}
