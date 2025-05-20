/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Shapes;

import javafx.scene.shape.Rectangle;
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
public class RectangleShapeTest {
    
    public RectangleShapeTest() {
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
     * Test del metodo toFXShape().
     * Verifica che le coordinate, le dimensioni e i colori del rettangolo JavaFX
     * corrispondano ai valori logici impostati nella classe RectangleShape.
     */
    @Test
    public void testToFXShape_CorrectDimensionsAndColors() {
        RectangleShape rectShape = new RectangleShape(10, 20, 30, 50);
        rectShape.perimetralColorString = "RED";
        rectShape.internalColorString = "BLUE";

        Rectangle fxRect = rectShape.toFXShape();

        assertEquals(10, fxRect.getX(), 0.001);
        assertEquals(20, fxRect.getY(), 0.001);
        assertEquals(20, fxRect.getWidth(), 0.001);
        assertEquals(30, fxRect.getHeight(), 0.001);
        assertEquals("0xff0000ff", fxRect.getStroke().toString()); // RED
        assertEquals("0x0000ffff", fxRect.getFill().toString());   // BLUE
    }
    
    /**
    * Test toFXShape: Coordinate normali e positive.
    */
   @Test
   public void testToFXShape_NormalCoordinates() {
       RectangleShape shape = new RectangleShape(10, 20, 40, 60);
       Rectangle rect = shape.toFXShape();
       assertEquals(10, rect.getX(), 0.001);
       assertEquals(20, rect.getY(), 0.001);
       assertEquals(30, rect.getWidth(), 0.001);
       assertEquals(40, rect.getHeight(), 0.001);
   }

   /**
    * Test toFXShape: Coordinate invertite (finalX < initialX).
    */
   @Test
   public void testToFXShape_InvertedCoordinates() {
       RectangleShape shape = new RectangleShape(100, 100, 50, 50);
       Rectangle rect = shape.toFXShape();
       assertEquals(50, rect.getX(), 0.001);
       assertEquals(50, rect.getY(), 0.001);
       assertEquals(50, rect.getWidth(), 0.001);
       assertEquals(50, rect.getHeight(), 0.001);
   }

   /**
    * Test toFXShape: Nessun colore impostato (usa default di JavaFX).
    */
   @Test
   public void testToFXShape_NoColorSet() {
       RectangleShape shape = new RectangleShape(0, 0, 20, 20);
       Rectangle rect = shape.toFXShape();
       assertNotNull(rect.getStroke()); // JavaFX assegna comunque un colore
       assertNotNull(rect.getFill());
   }

   /**
    * Test toFXShape: Colori personalizzati.
    */
   @Test
   public void testToFXShape_CustomColors() {
       RectangleShape shape = new RectangleShape(0, 0, 10, 10);
       shape.perimetralColorString = "BLACK";
       shape.internalColorString = "WHITE";
       Rectangle rect = shape.toFXShape();
       assertEquals("0x000000ff", rect.getStroke().toString());
       assertEquals("0xffffffff", rect.getFill().toString());
   }

   /**
    * Test toFXShape: Coordinate con decimali.
    */
   @Test
   public void testToFXShape_DecimalCoordinates() {
       RectangleShape shape = new RectangleShape(1.5, 2.5, 4.0, 5.0);
       Rectangle rect = shape.toFXShape();
       assertEquals(1.5, rect.getX(), 0.001);
       assertEquals(2.5, rect.getY(), 0.001);
       assertEquals(2.5, rect.getWidth(), 0.001);
       assertEquals(2.5, rect.getHeight(), 0.001);
   }

    /**
     * Test del metodo getType().
     * Verifica che venga restituita correttamente la stringa "RECTANGLE".
     */
    @Test
    public void testGetType_ReturnsRectangle() {
        RectangleShape rectShape = new RectangleShape(0, 0, 10, 10);
        assertEquals("RECTANGLE", rectShape.getType());
    }
    
    /**
 * Test getType: valore atteso.
 */
    @Test
    public void testGetType_ShouldReturnRectangle() {
        RectangleShape shape = new RectangleShape(0, 0, 1, 1);
        assertEquals("RECTANGLE", shape.getType());
    }

    /**
     * Test getType: con coordinate negative.
     */
    @Test
    public void testGetType_WithNegativeCoords() {
        RectangleShape shape = new RectangleShape(-5, -5, -1, -1);
        assertEquals("RECTANGLE", shape.getType());
    }

    /**
     * Test getType: su shape con colori impostati.
     */
    @Test
    public void testGetType_WithColorsSet() {
        RectangleShape shape = new RectangleShape(0, 0, 2, 2);
        shape.internalColorString = "GREEN";
        shape.perimetralColorString = "RED";
        assertEquals("RECTANGLE", shape.getType());
    }

    /**
     * Test getType: dopo chiamata a toFXShape().
     */
    @Test
    public void testGetType_AfterToFXShapeCall() {
        RectangleShape shape = new RectangleShape(0, 0, 1, 1);
        shape.toFXShape(); // chiamata per sicurezza
        assertEquals("RECTANGLE", shape.getType());
    }

    /**
     * Test del metodo clone().
     * Verifica che:
     * - L'oggetto clonato non sia lo stesso riferimento dell'originale.
     * - Le proprietà logiche (coordinate e colori) siano identiche.
     * - Anche la rappresentazione grafica JavaFX sia equivalente.
     */
    @Test
    public void testClone_CreatesIdenticalCopy() {
        RectangleShape original = new RectangleShape(5, 5, 25, 35);
        original.perimetralColorString = "GREEN";
        original.internalColorString = "YELLOW";

        RectangleShape copy = (RectangleShape) original.clone();

        assertNotSame(original, copy);
        assertEquals(original.initialX, copy.initialX, 0.001);
        assertEquals(original.initialY, copy.initialY, 0.001);
        assertEquals(original.finalX, copy.finalX, 0.001);
        assertEquals(original.finalY, copy.finalY, 0.001);
        assertEquals(original.perimetralColorString, copy.perimetralColorString);
        assertEquals(original.internalColorString, copy.internalColorString);

        Rectangle fxOriginal = original.toFXShape();
        Rectangle fxCopy = (Rectangle) copy.getFXShape();
        assertEquals(fxOriginal.getX(), fxCopy.getX(), 0.001);
        assertEquals(fxOriginal.getY(), fxCopy.getY(), 0.001);
        assertEquals(fxOriginal.getWidth(), fxCopy.getWidth(), 0.001);
        assertEquals(fxOriginal.getHeight(), fxCopy.getHeight(), 0.001);
        assertEquals(fxOriginal.getStroke(), fxCopy.getStroke());
        assertEquals(fxOriginal.getFill(), fxCopy.getFill());
    }
    
        /**
     * Test clone: proprietà base identiche.
     */
    @Test
    public void testClone_PropertiesMatch() {
        RectangleShape original = new RectangleShape(0, 0, 10, 10);
        original.internalColorString = "RED";
        original.perimetralColorString = "BLUE";

        RectangleShape copy = (RectangleShape) original.clone();
        assertEquals(original.initialX, copy.initialX, 0.001);
        assertEquals(original.finalY, copy.finalY, 0.001);
        assertEquals(original.internalColorString, copy.internalColorString);
        assertEquals(original.perimetralColorString, copy.perimetralColorString);
    }

    /**
     * Test clone: gli oggetti sono distinti.
     */
    @Test
    public void testClone_IsDifferentInstance() {
        RectangleShape original = new RectangleShape(0, 0, 10, 10);
        Shape copy = original.clone();
        assertNotSame(original, copy);
    }

    /**
     * Test clone: JavaFX shape copiata.
     */
    @Test
    public void testClone_HasFXShape() {
        RectangleShape original = new RectangleShape(0, 0, 10, 10);
        original.internalColorString = "WHITE";
        original.perimetralColorString = "BLACK";
        RectangleShape copy = (RectangleShape) original.clone();

        assertNotNull(copy.getFXShape());
        Rectangle rect = (Rectangle) copy.getFXShape();
        assertEquals("0xffffffff", rect.getFill().toString());
        assertEquals("0x000000ff", rect.getStroke().toString());
    }

    /**
     * Test clone: copia funziona con coordinate invertite.
     */
    @Test
    public void testClone_InvertedCoordinates() {
        RectangleShape original = new RectangleShape(10, 10, 0, 0);
        RectangleShape copy = (RectangleShape) original.clone();

        Rectangle rect = (Rectangle) copy.getFXShape();
        assertEquals(0, rect.getX(), 0.001);
        assertEquals(0, rect.getY(), 0.001);
        assertEquals(10, rect.getWidth(), 0.001);
        assertEquals(10, rect.getHeight(), 0.001);
    }

    /**
     * Test clone: con valori decimali.
     */
    @Test
    public void testClone_WithDecimalValues() {
        RectangleShape original = new RectangleShape(1.1, 2.2, 3.3, 4.4);
        original.internalColorString = "CYAN";
        original.perimetralColorString = "MAGENTA";

        RectangleShape copy = (RectangleShape) original.clone();
        Rectangle rect = (Rectangle) copy.getFXShape();
        assertEquals(1.1, rect.getX(), 0.001);
        assertEquals(2.2, rect.getY(), 0.001);
        assertEquals(2.2, rect.getWidth(), 0.001);
        assertEquals(2.2, rect.getHeight(), 0.001);
    }
    
}
