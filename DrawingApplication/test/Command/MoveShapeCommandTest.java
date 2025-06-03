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
 *   1) execute() sposti correttamente la Shape di (dx, dy),
 *   2) undo() riporti la Shape alla posizione originale.
 */
public class MoveShapeCommandTest {
    
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
        new JFXPanel();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void execute_ShouldTranslateShapeByDxDy() {
        // Arrange:
        // 1) Creo una RectangleShape con coordinate iniziali [0,0] e finali [10,10]
        RectangleShape rect = new RectangleShape(0, 0, 10, 10);

        // 2) Inserisco la Shape in drawList affinché DrawingReceiver possa ridisegnare
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Verifico le coordinate iniziali
        assertEquals("InitialX iniziale deve essere 0", 0.0, rect.getInitialX(), 0.0);
        assertEquals("InitialY iniziale deve essere 0", 0.0, rect.getInitialY(), 0.0);
        assertEquals("FinalX iniziale deve essere 10", 10.0, rect.getFinalX(), 0.0);
        assertEquals("FinalY iniziale deve essere 10", 10.0, rect.getFinalY(), 0.0);

        // 5) Definisco uno spostamento dx = 5, dy = 7
        double dx = 5.0;
        double dy = 7.0;

        // 6) Creo il comando MoveShapeCommand con la firma
        //    (Shape shape, double dx, double dy, DrawingReceiver receiver)
        MoveShapeCommand command = new MoveShapeCommand(rect, dx, dy, receiver);

        // Act:
        command.execute();

        // Assert:
        // Dopo execute(), ci aspettiamo che la Shape sia stata traslata di (5,7),
        // quindi initial = [5,7], final = [15,17].
        assertEquals("Dopo execute, initialX deve essere 5", 5.0, rect.getInitialX(), 0.0);
        assertEquals("Dopo execute, initialY deve essere 7", 7.0, rect.getInitialY(), 0.0);
        assertEquals("Dopo execute, finalX deve essere 15", 15.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo execute, finalY deve essere 17", 17.0, rect.getFinalY(), 0.0);
    }

    @Test
    public void undo_ShouldRestoreShapeToOriginalPosition() {
        // Arrange:
        // 1) Creo una RectangleShape con coordinate iniziali [0,0] e finali [10,10]
        RectangleShape rect = new RectangleShape(0, 0, 10, 10);

        // 2) Inserisco la Shape in drawList
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Definisco dx = -3, dy = 4 per testare uno spostamento negativo/parziale
        double dx = -3.0;
        double dy = 4.0;

        // 5) Creo il comando MoveShapeCommand e chiamo execute() per spostare la Shape
        MoveShapeCommand command = new MoveShapeCommand(rect, dx, dy, receiver);
        command.execute();

        // Verifico che dopo execute, la Shape sia a initial = [-3,4], final = [7,14]
        assertEquals("Dopo execute, initialX deve essere -3", -3.0, rect.getInitialX(), 0.0);
        assertEquals("Dopo execute, initialY deve essere 4", 4.0, rect.getInitialY(), 0.0);
        assertEquals("Dopo execute, finalX deve essere 7", 7.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo execute, finalY deve essere 14", 14.0, rect.getFinalY(), 0.0);

        // Act:
        command.undo();

        // Assert:
        // Dopo undo(), la Shape deve essere tornata a initial = [0,0], final = [10,10]
        assertEquals("Dopo undo, initialX deve tornare a 0", 0.0, rect.getInitialX(), 0.0);
        assertEquals("Dopo undo, initialY deve tornare a 0", 0.0, rect.getInitialY(), 0.0);
        assertEquals("Dopo undo, finalX deve tornare a 10", 10.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo undo, finalY deve tornare a 10", 10.0, rect.getFinalY(), 0.0);
    }
    
}
