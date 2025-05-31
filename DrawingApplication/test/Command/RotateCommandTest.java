/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * Classe di test per RotateCommand.
 * Verifica l'esecuzione e l'annullamento della rotazione di una forma.
 * 
 * @author gaetanof
 */
public class RotateCommandTest {

    private Shape shape;                       // Oggetto forma da ruotare
    private javafx.scene.shape.Shape fxShape;  // Controparte JavaFX della forma

    public RotateCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Inizializza il toolkit JavaFX (necessario in modalità headless per i test)
        new JFXPanel();
    }

    @AfterClass
    public static void tearDownClass() {
        // Nessuna azione necessaria dopo tutti i test
    }

    @Before
    public void setUp() {
        // Crea un rettangolo semplice per effettuare i test
        shape = new RectangleShape(0, 0, 10, 10);
        fxShape = shape.toFXShape();
        shape.setFXShape(fxShape);

        // Aggiunta della forma a un contenitore (non strettamente necessario per questi test)
        new Pane().getChildren().add(fxShape);
    }

    @After
    public void tearDown() {
        // Pulizia dopo ogni test, se necessario
    }

    /**
     * Test del metodo execute con una rotazione di 90 gradi.
     * Verifica che la forma venga ruotata dell’angolo specificato.
     */
    @Test
    public void testExecute90Degrees() {
        System.out.println("Testing execute() con rotazione di 90 gradi");
        double angle = 90.0;
        RotateCommand instance = new RotateCommand(shape, angle);

        // La rotazione iniziale dovrebbe essere 0
        assertEquals(0.0, shape.getRotation(), 0.001);

        // Esecuzione della rotazione
        instance.execute();

        // Verifica che la rotazione sia esattamente 90 gradi
        assertEquals(90.0, shape.getRotation(), 0.001);
    }

    /**
     * Test del metodo execute con rotazioni multiple.
     * Verifica che le rotazioni si accumulino correttamente.
     */
    @Test
    public void testMultipleRotations() {
        System.out.println("Testing rotazioni multiple");

        // Prima rotazione di 45 gradi
        RotateCommand rotation1 = new RotateCommand(shape, 45.0);
        rotation1.execute();
        assertEquals(45.0, shape.getRotation(), 0.001);

        // Seconda rotazione di 45 gradi (totale atteso: 90)
        RotateCommand rotation2 = new RotateCommand(shape, 45.0);
        rotation2.execute();
        assertEquals(90.0, shape.getRotation(), 0.001);
    }

    /**
     * Test del metodo undo.
     * Verifica che l’annullamento ripristini correttamente la rotazione.
     */
    @Test
    public void testUndo() {
        System.out.println("Testing undo()");

        double angle = 45.0;
        RotateCommand instance = new RotateCommand(shape, angle);

        // Esegue la rotazione
        instance.execute();
        assertEquals(45.0, shape.getRotation(), 0.001);

        // Annulla la rotazione
        instance.undo();
        assertEquals(0.0, shape.getRotation(), 0.001);
    }

    /**
     * Test della normalizzazione dell’angolo di rotazione.
     * Verifica che una rotazione superiore a 360 gradi venga normalizzata.
     */
    @Test
    public void testRotationNormalization() {
        System.out.println("Testing normalizzazione della rotazione");

        // Rotazione di 400 gradi (atteso: 40 gradi)
        RotateCommand instance = new RotateCommand(shape, 400.0);
        instance.execute();

        // La rotazione dovrebbe essere normalizzata a 40 gradi
        assertEquals(40.0, shape.getRotation(), 0.001);
    }
}