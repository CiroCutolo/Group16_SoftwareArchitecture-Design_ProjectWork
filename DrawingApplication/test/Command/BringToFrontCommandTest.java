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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author genna
 */
/**
 * Qui usiamo due istanze concrete di RectangleShape e il DrawingReceiver reale
 * per verificare il corretto comportamento di execute() e undo():
 *
 *  - execute() (bringToFront) deve spostare la Shape in coda alla lista (indice massimo).
 *  - undo()   (sendToBack)   deve portare la Shape in testa alla lista (indice 0).
 */
public class BringToFrontCommandTest {
    
    public BringToFrontCommandTest() {
    }
    
    /**
     * Inizializza il toolkit JavaFX una volta sola prima di tutti i test,
     * altrimenti new Pane() genererebbe “Toolkit not initialized”.
     */
    @BeforeClass
    public static void setUpClass() {
        new JFXPanel();
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
    public void execute_ShouldMoveShapeToEndOfList() {
        // Arrange: creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // Creo il DrawingReceiver con la lista e un Pane (JavaFX già inizializzato)
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Verifico lo stato iniziale: s1 all'indice 0, s2 all'indice 1
        assertSame("Prima di execute, drawList[0] deve essere s1", s1, drawList.get(0));
        assertSame("Prima di execute, drawList[1] deve essere s2", s2, drawList.get(1));

        // Creo il comando per portare s1 in primo piano (coda della lista)
        BringToFrontCommand command = new BringToFrontCommand(s1, receiver);

        // Act: eseguo il comando
        command.execute();

        // Assert: ora s1 dovrebbe trovarsi in coda, ovvero all'indice 1,
        // e s2 deve trovarsi in testa (indice 0)
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));
    }

    @Test
    public void undo_ShouldMoveShapeBackToStartOfList() {
        // Arrange: creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // Creo il DrawingReceiver con la lista e un Pane
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Creo il comando e chiamo execute() per spostare s1 in coda
        BringToFrontCommand command = new BringToFrontCommand(s1, receiver);
        command.execute();

        // Verifico che ora la lista sia [s2, s1]
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));

        // Act: chiamo undo() per riportare s1 in testa (indice 0)
        command.undo();

        // Assert: la lista deve tornare [s1, s2]
        assertSame("Dopo undo, drawList[0] deve tornare s1", s1, drawList.get(0));
        assertSame("Dopo undo, drawList[1] deve tornare s2", s2, drawList.get(1));
    }
}
