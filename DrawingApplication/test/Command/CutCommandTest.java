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
 * Usiamo due istanze concrete di RectangleShape e il DrawingReceiver reale
 * per verificare che:
 *   1) execute() rimuova correttamente la Shape dalla lista drawShapes,
 *   2) undo() la reinserisca nella stessa posizione di partenza.
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
        new JFXPanel();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void execute_ShouldRemoveShapeAndPopulateClipboard() {
        // Arrange:
        // 1) Creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // 2) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 3) Creo una Clipboard vuota
        Clipboard clipboard = new Clipboard();

        // Verifico precondizioni
        assertEquals("All’inizio, drawList contiene 2 forme", 2, drawList.size());
        assertFalse("All’inizio, la clipboard deve essere vuota", clipboard.getContents().size() > 0);

        // 4) Creo il CutCommand con la firma corretta (Shape, Clipboard, DrawingReceiver)
        CutCommand command = new CutCommand(s1, clipboard, receiver);

        // Act:
        command.execute();

        // Assert:
        //  - s1 deve essere rimosso da drawList, restando solo [s2]
        assertEquals("Dopo execute, drawList deve avere 1 forma", 1, drawList.size());
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));

        //  - la clipboard deve contenere un clone di s1
        List<Shape> clipContents = clipboard.getContents();
        assertEquals("Dopo execute, clipboard.getContents() deve avere 1 elemento", 1, clipContents.size());
        Shape clippedShape = clipContents.get(0);

        // Verifico che il clone abbia le stesse coordinate iniziali/finali di s1
        assertNotSame("La forma nella clipboard non deve essere la stessa istanza di s1", s1, clippedShape);
        assertEquals("Il clone deve avere lo stesso initialX di s1",
                     s1.getInitialX(), clippedShape.getInitialX(), 0.0);
        assertEquals("Il clone deve avere lo stesso initialY di s1",
                     s1.getInitialY(), clippedShape.getInitialY(), 0.0);
        assertEquals("Il clone deve avere lo stesso finalX di s1",
                     s1.getFinalX(), clippedShape.getFinalX(), 0.0);
        assertEquals("Il clone deve avere lo stesso finalY di s1",
                     s1.getFinalY(), clippedShape.getFinalY(), 0.0);
    }

    @Test
    public void undo_ShouldReinsertCloneAtEndOfDrawList() {
        // Arrange:
        // 1) Creo due RectangleShape e li metto in drawShapes in ordine [s1, s2]
        Shape s1 = new RectangleShape(0, 0, 10, 10);
        Shape s2 = new RectangleShape(20, 20, 30, 30);

        List<Shape> drawList = new ArrayList<>();
        drawList.add(s1);
        drawList.add(s2);

        // 2) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 3) Creo una Clipboard vuota
        Clipboard clipboard = new Clipboard();

        // 4) Creo il CutCommand e chiamo execute() per rimuovere s1
        CutCommand command = new CutCommand(s1, clipboard, receiver);
        command.execute();

        // Verifico che dopo execute, drawList sia [s2]
        assertEquals("Dopo execute, drawList deve avere 1 forma", 1, drawList.size());
        assertSame("Dopo execute, drawList[0] deve essere s2", s2, drawList.get(0));

        // Act:
        command.undo();

        // Assert:
        //  - drawList deve contenere ora due forme: [s2, cloneDiS1]
        assertEquals("Dopo undo, drawList deve avere 2 forme", 2, drawList.size());
        assertSame("Dopo undo, drawList[0] deve rimanere s2", s2, drawList.get(0));

        Shape reinserted = drawList.get(1);
        //  - La forma reinserita non deve essere la stessa istanza di s1
        assertNotSame("La forma reinserita non deve essere la stessa istanza di s1", s1, reinserted);
        //  - Deve avere le stesse coordinate iniziali/finali di s1
        assertEquals("La forma reinserita deve avere initialX uguale a s1",
                     s1.getInitialX(), reinserted.getInitialX(), 0.0);
        assertEquals("La forma reinserita deve avere initialY uguale a s1",
                     s1.getInitialY(), reinserted.getInitialY(), 0.0);
        assertEquals("La forma reinserita deve avere finalX uguale a s1",
                     s1.getFinalX(), reinserted.getFinalX(), 0.0);
        assertEquals("La forma reinserita deve avere finalY uguale a s1",
                     s1.getFinalY(), reinserted.getFinalY(), 0.0);
    }
    
}
