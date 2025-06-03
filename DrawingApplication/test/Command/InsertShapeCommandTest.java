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

 * Qui verifichiamo che:
 *   1) execute() inserisca la Shape in coda alla lista drawShapes,
 *   2) undo() rimuova la stessa Shape dalla lista.
 */
public class InsertShapeCommandTest {
    
    public InsertShapeCommandTest() {
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
    public void execute_ShouldInsertShapeAtEndOfDrawList() {
        // Arrange:
        // 1) Creo una RectangleShape s1 e la metto in drawShapes
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        // 2) Creo una nuova RectangleShape s2 che inseriremo col comando
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Verifico lo stato iniziale
        assertEquals("Inizialmente drawList deve contenere 1 forma", 1, drawList.size());
        assertSame("drawList[0] deve essere s1", s1, drawList.get(0));

        // 4) Creo il comando InsertShapeCommand con la firma (Shape, DrawingReceiver)
        InsertShapeCommand command = new InsertShapeCommand(s2, receiver);

        // Act:
        command.execute();

        // Assert:
        // Dopo execute(), drawList deve avere 2 forme, con s2 in coda
        assertEquals("Dopo execute, drawList deve contenere 2 forme", 2, drawList.size());
        assertSame("Dopo execute, drawList[0] deve rimanere s1", s1, drawList.get(0));
        assertSame("Dopo execute, drawList[1] deve essere s2", s2, drawList.get(1));
    }

    @Test
    public void undo_ShouldRemoveShapeFromDrawList() {
        // Arrange:
        // 1) Creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // 2) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // Verifico lo stato iniziale
        assertEquals("Inizialmente drawList deve contenere 2 forme", 2, drawList.size());
        assertSame("drawList[0] deve essere s1", s1, drawList.get(0));
        assertSame("drawList[1] deve essere s2", s2, drawList.get(1));

        // 3) Creo il comando e chiamo execute() per inserire s3
        Shape s3 = new RectangleShape(40, 40, 50, 50);
        InsertShapeCommand command = new InsertShapeCommand(s3, receiver);
        command.execute();

        // Verifico che la lista sia ora [s1, s2, s3]
        assertEquals("Dopo execute, drawList deve contenere 3 forme", 3, drawList.size());
        assertSame("Dopo execute, drawList[2] deve essere s3", s3, drawList.get(2));

        // Act:
        command.undo();

        // Assert:
        // Dopo undo(), s3 deve essere stato rimosso, quindi drawList torna [s1, s2]
        assertEquals("Dopo undo, drawList deve tornare a contenere 2 forme", 2, drawList.size());
        assertSame("Dopo undo, drawList[0] deve rimanere s1", s1, drawList.get(0));
        assertSame("Dopo undo, drawList[1] deve rimanere s2", s2, drawList.get(1));
    }
}
