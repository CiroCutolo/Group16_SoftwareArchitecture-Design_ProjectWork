/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Shapes;

import javafx.scene.text.Text;
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
public class TextShapeTest {

    private TextShape textShape;

    @BeforeClass
    public static void setUpClass() {
        // Niente per ora
    }

    @AfterClass
    public static void tearDownClass() {
        // Niente per ora
    }

    @Before
    public void setUp() {
        textShape = new TextShape("Hello", 100, 200);
    }

    @After
    public void tearDown() {
        textShape = null;
    }

    @Test
    public void testToFXShape() {
        Text fxText = textShape.toFXShape();
        assertNotNull(fxText);
        assertEquals("Hello", fxText.getText());
        assertEquals(100.0, fxText.getX(), 0.01);
        assertEquals(200.0, fxText.getY(), 0.01);
    }

    @Test
    public void testGetType() {
        assertEquals("TEXT", textShape.getType());
    }

    @Test
    public void testClone() {
        TextShape clone = (TextShape) textShape.clone();
        assertNotSame(textShape, clone);
        assertEquals(textShape.getTextContent(), clone.getTextContent());
        assertEquals(textShape.getFontSize(), clone.getFontSize(), 0.01);
    }

    @Test
    public void testResizeDoesNothing() {
        double oldFontSize = textShape.getFontSize();
        textShape.resize(500, 500);
        assertEquals(oldFontSize, textShape.getFontSize(), 0.01);
    }

    @Test
    public void testGetTextContent() {
        assertEquals("Hello", textShape.getTextContent());
    }

    @Test
    public void testSetTextContent() {
        textShape.setTextContent("NewText");
        assertEquals("NewText", textShape.getTextContent());
    }

    @Test
    public void testGetFontSize() {
        assertEquals(20.0, textShape.getFontSize(), 0.01);
    }

    @Test
    public void testSetFontSize() {
        textShape.setFontSize(32.0);
        assertEquals(32.0, textShape.getFontSize(), 0.01);
    }
}
