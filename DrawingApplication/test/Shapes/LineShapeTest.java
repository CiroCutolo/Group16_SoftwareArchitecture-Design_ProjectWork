/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Shapes;

import javafx.scene.shape.Line;
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
public class LineShapeTest {
    
    public LineShapeTest() {
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
     * Test of toFXShape method, of class LineShape.
     */

        @Test
    public void testToFXShape_BasicLine() {
        LineShape shape = new LineShape(10, 20, 30, 40);
        shape.perimetralColorString = "BLACK";
        shape.internalColorString = "WHITE"; // anche se non usata in Line

        Line line = shape.toFXShape();

        assertEquals(10, line.getStartX(), 0.001);
        assertEquals(20, line.getStartY(), 0.001);
        assertEquals(30, line.getEndX(), 0.001);
        assertEquals(40, line.getEndY(), 0.001);
        assertEquals("0x000000ff", line.getStroke().toString());
    }
    
        @Test
    public void testToFXShape_SamePoints() {
        LineShape shape = new LineShape(5, 5, 5, 5);
        shape.perimetralColorString = "RED";
        shape.internalColorString = "BLUE";

        Line line = shape.toFXShape();

        assertEquals(5, line.getStartX(), 0.001);
        assertEquals(5, line.getStartY(), 0.001);
        assertEquals(5, line.getEndX(), 0.001);
        assertEquals(5, line.getEndY(), 0.001);
    }
    
        @Test
    public void testToFXShape_DecimalCoordinates() {
        LineShape shape = new LineShape(1.5, 2.5, 3.5, 4.5);
        shape.perimetralColorString = "BLUE";
        shape.internalColorString = "WHITE";

        Line line = shape.toFXShape();

        assertEquals(1.5, line.getStartX(), 0.001);
        assertEquals(2.5, line.getStartY(), 0.001);
        assertEquals(3.5, line.getEndX(), 0.001);
        assertEquals(4.5, line.getEndY(), 0.001);
    }
    
    


    /**
     * Test of getType method, of class LineShape.
     */
    
    @Test
    public void testGetType_BaseTest() {
        LineShape shape = new LineShape(0, 0, 10, 10);
        assertEquals("LINE", shape.getType());
    }
    
        @Test
    public void testGetType_AfterToFXShape() {
        LineShape shape = new LineShape(0, 0, 10, 10);
        shape.toFXShape();
        assertEquals("LINE", shape.getType());
    }



    /**
     * Test of clone method, of class LineShape.
     */
    
        @Test
    public void testClone_CorrectProperties() {
        LineShape shape = new LineShape(1, 2, 3, 4);
        shape.perimetralColorString = "CYAN";

        LineShape copy = (LineShape) shape.clone();

        assertNotSame(shape, copy);
        assertEquals(shape.initialX, copy.initialX, 0.001);
        assertEquals(shape.finalY, copy.finalY, 0.001);
        assertEquals("CYAN", copy.perimetralColorString);
    }
    
        @Test
    public void testClone_HasValidFXShape() {
        LineShape shape = new LineShape(10, 10, 20, 20);
        shape.perimetralColorString = "MAGENTA";
        shape.internalColorString = "TRANSPARENT";

        LineShape copy = (LineShape) shape.clone();
        Line line = (Line) copy.getFXShape();

        assertEquals(10, line.getStartX(), 0.001);
        assertEquals(20, line.getEndX(), 0.001);
        assertEquals("0xff00ffff", line.getStroke().toString());
    }

}
