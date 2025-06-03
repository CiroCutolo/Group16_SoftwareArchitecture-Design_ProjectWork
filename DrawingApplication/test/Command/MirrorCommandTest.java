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
 * Usiamo un’istanza concreta di RectangleShape e un DummyReceiver
 * (sottoclasse minimale di DrawingReceiver) per verificare che:
 *   1) execute() invochi mirrorShape(shape, horizontal) sul receiver
 *   2) undo() invochi mirrorShape(shape, horizontal) sul receiver di nuovo
 */
public class MirrorCommandTest {
    
    public MirrorCommandTest() {
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
    public void execute_ShouldToggleHorizontalMirrorState() {
        // Arrange:
        // 1) Creo una RectangleShape e mi aspetto che all’inizio isMirroredX() sia false
        RectangleShape rect = new RectangleShape(0, 0, 10, 10);
        assertFalse("Di default, isMirroredX() deve essere false", rect.isMirroredX());

        // 2) Metto la shape in drawList affinché DrawingReceiver possa ridisegnare
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Costruisco MirrorCommand con horizontal = true
        MirrorCommand command = new MirrorCommand(rect, true, receiver);

        // Act: eseguo il comando
        command.execute();

        // Assert:
        // Dopo execute(), l’asse orizzontale deve essere “mirrored” (toggled)
        assertTrue("Dopo execute(), isMirroredX() deve essere true", rect.isMirroredX());
        // L’asse verticale non deve essere cambiato
        assertFalse("Dopo execute(), isMirroredY() deve rimanere false", rect.isMirroredY());
    }

    @Test
    public void undo_ShouldToggleHorizontalMirrorBackToOriginal() {
        // Arrange:
        // 1) Creo una RectangleShape e mi aspetto isMirroredX() false
        RectangleShape rect = new RectangleShape(5, 5, 15, 15);
        assertFalse("Di default, isMirroredX() deve essere false", rect.isMirroredX());

        // 2) Metto la shape in drawList
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Costruisco MirrorCommand con horizontal = false (verifichiamo mirrorY)
        MirrorCommand command = new MirrorCommand(rect, false, receiver);

        // Act: eseguo execute() per specchiare sull’asse verticale
        command.execute();

        // Verifico che isMirroredY() sia ora true (e isMirroredX() rimanga false)
        assertTrue("Dopo execute(), isMirroredY() deve essere true", rect.isMirroredY());
        assertFalse("Dopo execute(), isMirroredX() deve rimanere false", rect.isMirroredX());

        // Act: chiamo undo(), che esegue di nuovo mirrorShape con gli stessi parametri
        command.undo();

        // Assert:
        // Dopo il secondo specchiamento, lo stato verticale deve tornare false
        assertFalse("Dopo undo(), isMirroredY() deve tornare false", rect.isMirroredY());
        // E l’asse orizzontale deve ancora essere false
        assertFalse("Dopo undo(), isMirroredX() deve rimanere false", rect.isMirroredX());
    }
}
