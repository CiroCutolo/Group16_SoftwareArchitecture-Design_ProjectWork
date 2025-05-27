/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.RectangleShape;
import Shapes.Shape;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
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
public class MoveShapeCommandTest {
    
    private Shape shape;                       // shape da muovere
    private javafx.scene.shape.Shape fxShape;  // corrispettivo JavaFX
    
    public MoveShapeCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /* Inizializza il toolkit JavaFX (necessario in modalità headless). */
        new JFXPanel();

        /* La forma parte con traslazione (0,0). */
        shape   = new RectangleShape(0, 0, 10, 10);
        fxShape = shape.toFXShape();
        shape.setFXShape(fxShape);

        /* Se in futuro servirà, abbiamo già un Pane pronto. */
        new Pane().getChildren().add(fxShape);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Verifica che execute() sposti la shape delle quantità richieste.
     */
    @Test
    public void testExecute_MovesShape() {
        double dx = 15.0;
        double dy = -5.0;
        MoveShapeCommand command = new MoveShapeCommand(shape, dx, dy);

        /* Valori prima dell'esecuzione. */
        double xBefore = fxShape.getTranslateX();
        double yBefore = fxShape.getTranslateY();

        command.execute();

        assertEquals(xBefore + dx, fxShape.getTranslateX(), 0.0001);
        assertEquals(yBefore + dy, fxShape.getTranslateY(), 0.0001);
    }

    /**
     * Dopo undo() la forma deve tornare alla posizione iniziale.
     */
    @Test
    public void testUndo_RestoresOriginalPosition() {
        double dx = 8.0;
        double dy = 12.0;
        MoveShapeCommand command = new MoveShapeCommand(shape, dx, dy);

        command.execute(); // primo spostamento
        command.undo();    // annullo

        assertEquals(0.0, fxShape.getTranslateX(), 0.0001);
        assertEquals(0.0, fxShape.getTranslateY(), 0.0001);
    }

    /**
     * Se la shape è null, MoveShapeCommand deve lanciare una NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testExecute_NullShape_ThrowsException() {
        MoveShapeCommand command = new MoveShapeCommand(null, 5.0, 5.0);
        command.execute();
    }
    
}
