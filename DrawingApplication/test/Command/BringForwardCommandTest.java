/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Command;

import Shapes.RectangleShape;
import Shapes.Shape;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author genna
 */

/**
 * Unit test JUnit 4 per la classe BringForwardCommand, compatibile con Java 8.
 *
 * Qui definiamo due classi di supporto "fatte in casa":
 * 1) DummyShape   una sottoclasse minimale di Shape che implementa i metodi astratti in modo stub.
 * 2) DummyReceiver  estende DrawingReceiver e sovrascrive solo bringForward() e sendBackward()
 *    per registrare una chiamata (senza eseguire la logica di DrawingReceiver originale, che coinvolge
 *    JavaFX e manipolazione di liste/riferimenti).
 *
 * Inizializziamo il toolkit JavaFX in @BeforeClass anziché @BeforeAll, perché usiamo JUnit 4.
 */
public class BringForwardCommandTest {
    
    public BringForwardCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Inizializza il toolkit JavaFX per permettere la creazione di Pane senza errori
     * tipo "Toolkit not initialized". Senza questo, new Pane() potrebbe lanciare eccezioni.
     */
    @Before
    public void setUp() {
        new JFXPanel();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Sottoclasse minimale di Shape. I metodi astratti vengono stub-izzati perché in questi test
     * non servono le funzionalità grafiche: è sufficiente che esistano.
     */
    static class DummyShape extends Shape {

        public DummyShape(double initialX, double initialY, double finalX, double finalY) {
            super(initialX, initialY, finalX, finalY);
        }
        @Override
        public javafx.scene.shape.Shape toFXShape() {
            // Non serve restituire nulla di significativo per il test
            return null;
        }

        @Override
        public String getType() {
            return "Dummy";
        }

        @Override
        public Shape clone() {
            // Restituiamo lo stesso oggetto per semplicità
            return this;
        }
    }

    @Test
    public void execute_ShouldMoveShapeOnePositionForward() {
        // Arrange: creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // Creo il DrawingReceiver con la lista e un Pane (inizializzato)
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Verifico lo stato iniziale: s1 a indice 0, s2 a indice 1
        assertSame("Prima dell'execute, drawList[0] deve essere s1", s1, drawList.get(0));
        assertSame("Prima dell'execute, drawList[1] deve essere s2", s2, drawList.get(1));

        // Creo il comando per portare s1 "forward" (cioè in avanti nella lista)
        BringForwardCommand command = new BringForwardCommand(s1, receiver);

        // Act: eseguo il comando
        command.execute();

        // Assert: ora s1 dovrebbe essere in posizione 1 e s2 in posizione 0
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));
    }

    @Test
    public void undo_ShouldMoveShapeBackToOriginalPosition() {
        // Arrange: creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // Creo il DrawingReceiver con la lista e un Pane
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Creo il comando e chiamo execute() per spostare s1 avanti
        BringForwardCommand command = new BringForwardCommand(s1, receiver);
        command.execute();

        // Verifico che ora la lista sia [s2, s1]
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));

        // Act: chiamo undo() per riportare s1 indietro
        command.undo();

        // Assert: la lista deve tornare [s1, s2]
        assertSame("Dopo undo, drawList[0] deve tornare s1", s1, drawList.get(0));
        assertSame("Dopo undo, drawList[1] deve tornare s2", s2, drawList.get(1));
    }
}
