/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Shapes;

import javafx.scene.shape.Ellipse;
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
public class EllipseShapeTest {
    
    public EllipseShapeTest() {
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
     * Test: ellisse base.
     */
    @Test
    public void testToFXShape_NormalCase() {
        EllipseShape shape = new EllipseShape(100, 100, 150, 120);
        shape.perimetralColorString = "BLUE";
        shape.internalColorString = "RED";
        Ellipse ellipse = shape.toFXShape();

        assertEquals(125.0, ellipse.getCenterX(), 0.001);
        assertEquals(110.0, ellipse.getCenterY(), 0.001);
        assertEquals(25.0, ellipse.getRadiusX(), 0.001);
        assertEquals(10.0, ellipse.getRadiusY(), 0.001);
        assertEquals("0x0000ffff", ellipse.getStroke().toString());
        assertEquals("0xff0000ff", ellipse.getFill().toString());
    }
    
    /**
     * Test: Ellissi colorato.
     */
        @Test
    public void testToFXShape_WithColors() {
        EllipseShape shape = new EllipseShape(10, 20, 30, 40);
        shape.perimetralColorString = "RED";
        shape.internalColorString = "BLUE";

        Ellipse ellipse = shape.toFXShape();

        assertEquals(20.0, ellipse.getCenterX(), 0.001);
        assertEquals(30.0, ellipse.getCenterY(), 0.001);
        assertEquals(10.0, ellipse.getRadiusX(), 0.001);
        assertEquals(10.0, ellipse.getRadiusY(), 0.001);
        assertEquals("0xff0000ff", ellipse.getStroke().toString());
        assertEquals("0x0000ffff", ellipse.getFill().toString());
    }


    /**
     * Test: coordinate invertite.
     */
    @Test
    public void testToFXShape_InvertedCoordinates() {
        EllipseShape shape = new EllipseShape(200, 200, 100, 150);
        Ellipse ellipse = shape.toFXShape();

        assertEquals(150.0, ellipse.getCenterX(), 0.001);
        assertEquals(175.0, ellipse.getCenterY(), 0.001);
        assertEquals(50.0, ellipse.getRadiusX(), 0.001);
        assertEquals(25.0, ellipse.getRadiusY(), 0.001);
    }

    /**
     * Test: coordinate uguali (ellisse degenerata).
     */
    @Test
    public void testToFXShape_ZeroRadius() {
        EllipseShape shape = new EllipseShape(50, 50, 50, 50);
        Ellipse ellipse = shape.toFXShape();

        assertEquals(50.0, ellipse.getCenterX(), 0.001);
        assertEquals(50.0, ellipse.getCenterY(), 0.001);
        assertEquals(0.0, ellipse.getRadiusX(), 0.001);
        assertEquals(0.0, ellipse.getRadiusY(), 0.001);
    }

    /**
     * Test: colori nulli (nessun riempimento o bordo).
     */
        @Test
    public void testToFXShape_DifferentColorCombo() {
        EllipseShape shape = new EllipseShape(0, 0, 20, 10);
        shape.perimetralColorString = "BLACK";
        shape.internalColorString = "YELLOW";

        Ellipse ellipse = shape.toFXShape();

        assertEquals("0x000000ff", ellipse.getStroke().toString());
        assertEquals("0xffff00ff", ellipse.getFill().toString());
    }


    /**
     * Test: coordinate con decimali.
     */
    @Test
    public void testToFXShape_DecimalCoordinates() {
        EllipseShape shape = new EllipseShape(1.1, 2.2, 3.3, 4.4);
        Ellipse ellipse = shape.toFXShape();

        assertEquals(2.2, ellipse.getCenterX(), 0.001);
        assertEquals(3.3, ellipse.getCenterY(), 0.001);
        assertEquals(1.1, ellipse.getRadiusX(), 0.001);
        assertEquals(1.1, ellipse.getRadiusY(), 0.001);
    }

    /**
     * Test of getType method, of class EllipseShape.
     */
    @Test
    public void testGetType_ReturnsEllipse() {
        EllipseShape shape = new EllipseShape(1, 1, 2, 2);
        shape.perimetralColorString = "MAGENTA";
        shape.internalColorString = "WHITE";
        assertEquals("ELLIPSE", shape.getType());
    }

    
        /**
     * Test: tipo restituito è corretto.
     */
    @Test
    public void testGetType_ShouldReturnEllipse() {
        EllipseShape shape = new EllipseShape(0, 0, 10, 10);
        assertEquals("ELLIPSE", shape.getType());
    }

    /**
     * Test: dopo toFXShape().
     */
    @Test
    public void testGetType_AfterFXConversion() {
        EllipseShape shape = new EllipseShape(0, 0, 10, 10);
        shape.toFXShape(); // forza creazione grafica
        assertEquals("ELLIPSE", shape.getType());
    }


    /**
     * Test of clone method, of class EllipseShape.
     */
    @Test
    public void testClone_WithColors() {
        EllipseShape shape = new EllipseShape(5, 5, 15, 25);
        shape.perimetralColorString = "GREEN";
        shape.internalColorString = "CYAN";

        EllipseShape copy = (EllipseShape) shape.clone();

        assertNotSame(shape, copy);
        assertEquals(shape.initialX, copy.initialX, 0.001);
        assertEquals(shape.finalX, copy.finalX, 0.001);
        assertEquals("GREEN", copy.perimetralColorString);
        assertEquals("CYAN", copy.internalColorString);
    }

    
        /**
     * Test: clonazione corretta.
     */
    @Test
    public void testClone_CorrectProperties() {
        EllipseShape shape = new EllipseShape(10, 20, 30, 40);
        shape.perimetralColorString = "GREEN";
        shape.internalColorString = "YELLOW";

        EllipseShape clone = (EllipseShape) shape.clone();

        assertEquals(shape.initialX, clone.initialX, 0.001);
        assertEquals(shape.finalY, clone.finalY, 0.001);
        assertEquals(shape.perimetralColorString, clone.perimetralColorString);
        assertEquals(shape.internalColorString, clone.internalColorString);
    }

    /**
     * Test: oggetti distinti.
     */
    @Test
    public void testClone_IsDifferentInstance() {
        EllipseShape shape = new EllipseShape(1, 2, 3, 4);
        Shape copy = shape.clone();
        assertNotSame(shape, copy);
    }

    /**
     * Test: forma JavaFX copiata correttamente.
     */
    @Test
    public void testClone_HasEquivalentFXShape() {
        EllipseShape shape = new EllipseShape(5, 5, 15, 25);
        shape.perimetralColorString = "BLACK";
        shape.internalColorString = "CYAN";

        EllipseShape copy = (EllipseShape) shape.clone();
        Ellipse fxOriginal = shape.toFXShape();
        Ellipse fxCopy = (Ellipse) copy.getFXShape();

        assertEquals(fxOriginal.getCenterX(), fxCopy.getCenterX(), 0.001);
        assertEquals(fxOriginal.getRadiusX(), fxCopy.getRadiusX(), 0.001);
        assertEquals(fxOriginal.getFill(), fxCopy.getFill());
    }

    
}
