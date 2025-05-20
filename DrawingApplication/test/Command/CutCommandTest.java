/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

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
public class CutCommandTest {
    
    public CutCommandTest() {
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

    @Test
    public void testExecute_CutsSingleShape() {
        Clipboard clipboard = new Clipboard();
        List<Shape> selection = new ArrayList<>();
        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        Shape rect = new RectangleShape(0, 0, 10, 10);
        selection.add(rect);
        drawShapes.add(rect);

        // Crea una shape FX e aggiungila al pane manualmente
        javafx.scene.shape.Shape fxShape = rect.toFXShape();
        rect.setFXShape(fxShape); // Salva la reference nel campo interno
        pane.getChildren().add(fxShape);

        CutCommand cutCommand = new CutCommand(selection, clipboard, drawShapes, pane);
        cutCommand.execute();

        assertEquals(1, clipboard.getContents().size());
        assertFalse(drawShapes.contains(rect));

        // Ora il test funzionerà: l'oggetto da rimuovere è lo stesso
        assertFalse(pane.getChildren().contains(fxShape));
    }

        /**
     * Verifica che il comando non abbia effetti se la selezione è vuota.
     */
    @Test
    public void testExecute_EmptySelection() {
        Clipboard clipboard = new Clipboard();
        List<Shape> selection = new ArrayList<>();
        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        CutCommand cutCommand = new CutCommand(selection, clipboard, drawShapes, pane);
        cutCommand.execute();

        assertTrue(clipboard.getContents().isEmpty());
        assertTrue(drawShapes.isEmpty());
        assertTrue(pane.getChildren().isEmpty());
    }
    
        /**
     * Verifica che il comando non lanci eccezioni se la selezione è null.
     */
    @Test
    public void testExecute_NullSelection() {
        Clipboard clipboard = new Clipboard();
        List<Shape> drawShapes = new ArrayList<>();
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        CutCommand cutCommand = new CutCommand(null, clipboard, drawShapes, pane);
        cutCommand.execute();

        assertTrue(clipboard.getContents().isEmpty());
        assertTrue(drawShapes.isEmpty());
        assertTrue(pane.getChildren().isEmpty());
    }


}
