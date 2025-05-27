/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.RectangleShape;
import Shapes.Shape;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
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
public class ChangeColorCommandTest {
    private Shape shape;
    private javafx.scene.shape.Shape fxShape;

    private Color originalFill;
    private Color originalBorder;

    private Color newFill;
    private Color newBorder;
    
    public ChangeColorCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /* Necessario per usare JavaFX head-less. */
        new JFXPanel();

        shape   = new RectangleShape(0, 0, 20, 20);
        fxShape = shape.toFXShape();
        shape.setFXShape(fxShape);

        /* Colori di partenza */
        originalFill   = Color.BLACK;
        originalBorder = Color.GRAY;
        shape.setInternalColor(originalFill);
        shape.setPerimetralColor(originalBorder);
        fxShape.setFill(originalFill);
        fxShape.setStroke(originalBorder);

        /* Pane opzionale (non indispensabile). */
        new Pane().getChildren().add(fxShape);

        /* Nuovi colori da applicare */
        newFill   = Color.ORANGE;    // fill
        newBorder = Color.DARKBLUE;  // stroke
    }
    
    @After
    public void tearDown() {
    }

     /**
     * Verifica che execute() imposti correttamente fill e stroke.
     */
    @Test
    public void testExecute_ChangesFillAndBorder() {
        /* NB: ordine parametri = shape, newStroke, newFill */
        ChangeColorCommand command = new ChangeColorCommand(shape, newBorder, newFill);
        command.execute();

        assertEquals(newFill,   fxShape.getFill());
        assertEquals(newBorder, fxShape.getStroke());
    }

    /**
     * Verifica che undo() ripristini i colori originali.
     */
    @Test
    public void testUndo_RestoresOriginalFillAndBorder() {
        ChangeColorCommand command = new ChangeColorCommand(shape, newBorder, newFill);
        command.execute(); // applica
        command.undo();    // annulla

        assertEquals(originalFill,   fxShape.getFill());
        assertEquals(originalBorder, fxShape.getStroke());
    }

    /**
     * Shape nulla → NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testExecute_NullShape_ThrowsException() {
        new ChangeColorCommand(null, newBorder, newFill).execute();
    }
    
}
