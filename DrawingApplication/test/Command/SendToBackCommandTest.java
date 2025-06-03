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
 * Questi due test verificano che:
 *   1) execute() sposti correttamente la Shape in fondo alla lista drawShapes (indice 0),
 *   2) undo() riporti la Shape alla posizione originale.
 *
 * Si assume che:
 *   • La lista drawShapes esponga l’ordine di disegno (indice 0 = dietro, indice maggiore = primo piano).
 *   • SendToBackCommand abbia la firma: SendToBackCommand(Shape shape, DrawingReceiver receiver).
 *   • execute() invochi receiver.sendToBack(shape), che rimuove la shape dalla sua posizione
 *     corrente e la inserisce in testa alla lista (indice 0).
 *   • undo() invochi receiver.bringForward(shape), ripristinando la posizione originale.
 */

public class SendToBackCommandTest {
    
    public SendToBackCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        new JFXPanel();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void execute_ShouldMoveShapeToBackOfList() {
        // Arrange:
        // 1) Creo tre RectangleShape e li metto in drawShapes in ordine [s1, s2, s3]
        //    Interpretazione: s1 è dietro, s3 è in primo piano.
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);
        Shape s3 = new RectangleShape(40, 40, 50, 50);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);
        drawList.add(s3);

        // 2) Creo il DrawingReceiver con la lista e un Pane (JavaFX già inizializzato)
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Verifico lo stato iniziale: [s1, s2, s3]
        assertSame("Prima di execute, drawList[0] deve essere s1", s1, drawList.get(0));
        assertSame("Prima di execute, drawList[1] deve essere s2", s2, drawList.get(1));
        assertSame("Prima di execute, drawList[2] deve essere s3", s3, drawList.get(2));

        // 3) Istanzio il comando per inviare s3 in fondo alla lista (indice 0)
        SendToBackCommand command = new SendToBackCommand(s3, receiver);

        // Act:
        command.execute();

        // Assert:
        // Ora drawList dovrebbe essere [s3, s1, s2]
        assertSame("Dopo execute, drawList[0] deve essere s3", s3, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));
        assertSame("Dopo execute, drawList[2] deve essere s2", s2, drawList.get(2));
    }

    @Test
    public void undo_ShouldRestoreShapeToOriginalPosition() {
        // Arrange:
        // 1) Creo tre RectangleShape e li metto in drawShapes in ordine [s1, s2, s3]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);
        Shape s3 = new RectangleShape(40, 40, 50, 50);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);
        drawList.add(s3);

        // 2) Creo il DrawingReceiver con la lista e un Pane
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 3) Istanzio il comando e chiamo execute() per inviare s3 all’indice 0
        SendToBackCommand command = new SendToBackCommand(s3, receiver);
        command.execute();

        // Verifico che ora la lista sia [s3, s1, s2]
        assertSame("Dopo execute, drawList[0] deve essere s3", s3, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s1", s1, drawList.get(1));
        assertSame("Dopo execute, drawList[2] deve essere s2", s2, drawList.get(2));

        // Act:
        command.undo();

        // Assert:
        // Dopo undo, s3 deve tornare alla posizione originale (indice 2)
        // e la lista deve essere di nuovo [s1, s2, s3].
        assertSame("Dopo undo, drawList[0] deve rimanere s1", s1, drawList.get(0));
        assertSame("Dopo undo, drawList[1] deve rimanere s2", s2, drawList.get(1));
        assertSame("Dopo undo, drawList[2] deve tornare s3", s3, drawList.get(2));
    }
    
}
