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
/**Questi due test verificano che:
 *   1) execute() modifichi correttamente le dimensioni impostando finalX e finalY 
 *      in base a newWidth e newHeight (mantenendo initialX e initialY fissi);
 *   2) undo() ripristini i valori originali di finalX e finalY.
 *
 * Si assume che:
 *   • RectangleShape sia costruito con (initialX, initialY, finalX, finalY).
 *   • ResizeCommand abbia la firma: ResizeCommand(Shape shape, double newWidth, double newHeight, DrawingReceiver receiver).
 *   • execute() calcoli i nuovi finalX = initialX + newWidth, finalY = initialY + newHeight.
 *   • undo() ripristini finalX e finalY ai valori originali.
 */
public class ResizeCommandTest {
    
    public ResizeCommandTest() {
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
    public void execute_ShouldResizeShapeToNewDimensions() {
        // Arrange:
        // 1) Creo una RectangleShape con initial = (0, 0), final = (10, 10)
        //    => altezza e larghezza iniziali entrambe pari a 10
        RectangleShape rect = new RectangleShape(0, 0, 10, 10);

        // 2) Inserisco la shape in drawList affinché DrawingReceiver possa ridisegnare
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Verifico le coordinate iniziali del rettangolo
        assertEquals("initialX iniziale deve essere 0", 0.0, rect.getInitialX(), 0.0);
        assertEquals("initialY iniziale deve essere 0", 0.0, rect.getInitialY(), 0.0);
        assertEquals("finalX iniziale deve essere 10", 10.0, rect.getFinalX(), 0.0);
        assertEquals("finalY iniziale deve essere 10", 10.0, rect.getFinalY(), 0.0);

        // 5) Definisco le nuove dimensioni desiderate: newWidth = 20, newHeight = 30
        double newWidth = 20.0;
        double newHeight = 30.0;

        // 6) Creo il comando ResizeCommand con la firma corretta
        ResizeCommand command = new ResizeCommand(rect, newWidth, newHeight, receiver);

        // Act:
        command.execute();

        // Assert:
        // Dopo execute(), i valori di initialX/Y non devono cambiare
        assertEquals("Dopo execute, initialX deve rimanere 0", 0.0, rect.getInitialX(), 0.0);
        assertEquals("Dopo execute, initialY deve rimanere 0", 0.0, rect.getInitialY(), 0.0);

        // finalX e finalY devono essere aggiornati a (initialX + newWidth, initialY + newHeight)
        assertEquals("Dopo execute, finalX deve essere initialX + newWidth = 20",
                     20.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo execute, finalY deve essere initialY + newHeight = 30",
                     30.0, rect.getFinalY(), 0.0);
    }

    @Test
    public void undo_ShouldRestoreShapeToOriginalSize() {
        // Arrange:
        // 1) Creo una RectangleShape con initial = (5, 5), final = (15, 25)
        //    => larghezza iniziale = 10 (15 - 5), altezza iniziale = 20 (25 - 5)
        RectangleShape rect = new RectangleShape(5, 5, 15, 25);

        // 2) Inserisco la shape in drawList
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);

        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 4) Verifico le coordinate iniziali
        assertEquals("initialX iniziale deve essere 5", 5.0, rect.getInitialX(), 0.0);
        assertEquals("initialY iniziale deve essere 5", 5.0, rect.getInitialY(), 0.0);
        assertEquals("finalX iniziale deve essere 15", 15.0, rect.getFinalX(), 0.0);
        assertEquals("finalY iniziale deve essere 25", 25.0, rect.getFinalY(), 0.0);

        // 5) Scelgo nuove dimensioni: newWidth = 40, newHeight = 10
        double newWidth = 40.0;
        double newHeight = 10.0;
        //   => finalX atteso dopo execute() = initialX + newWidth = 5 + 40 = 45
        //   => finalY atteso dopo execute() = initialY + newHeight = 5 + 10 = 15

        // 6) Creo il comando e chiamo execute()
        ResizeCommand command = new ResizeCommand(rect, newWidth, newHeight, receiver);
        command.execute();

        // Controllo che l'esecuzione abbia funzionato
        assertEquals("Dopo execute, finalX deve essere 45", 45.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo execute, finalY deve essere 15", 15.0, rect.getFinalY(), 0.0);

        // Act:
        command.undo();

        // Assert:
        // Dopo undo(), finalX e finalY devono tornare ai valori originali (15 e 25)
        assertEquals("Dopo undo, initialX deve ancora essere 5", 5.0, rect.getInitialX(), 0.0);
        assertEquals("Dopo undo, initialY deve ancora essere 5", 5.0, rect.getInitialY(), 0.0);
        assertEquals("Dopo undo, finalX deve tornare a 15", 15.0, rect.getFinalX(), 0.0);
        assertEquals("Dopo undo, finalY deve tornare a 25", 25.0, rect.getFinalY(), 0.0);
    }
}
