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
import javafx.scene.paint.Color;
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
 * Qui usiamo un’istanza concreta di RectangleShape e il DrawingReceiver reale
 * per verificare che:
 * 1) execute() imposti correttamente i nuovi colori sulla Shape,
 * 2) undo() ripristini i colori originali.
 */
public class ChangeColorCommandTest {
    
    public ChangeColorCommandTest() {
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
    public void execute_ShouldChangeShapeColors() {
        // Arrange:
        // 1) Creo una RectangleShape con colori di default (perimetral = BLACK, internal = TRANSPARENT)
        Shape rect = new RectangleShape(0, 0, 10, 10);
        // 2) Inserisco la Shape in una lista per il DrawingReceiver
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);
        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());
        // 4) Verifico i colori iniziali attesi
        Color originalStroke = rect.getPerimetralColor();
        Color originalFill   = rect.getInternalColor();
        assertEquals("Il colore di contorno iniziale deve essere BLACK",
                     Color.BLACK, originalStroke);
        assertEquals("Il colore di riempimento iniziale deve essere TRANSPARENT",
                     Color.TRANSPARENT, originalFill);

        // 5) Scelgo nuovi colori
        Color newStroke = Color.RED;
        Color newFill   = Color.BLUE;
        // 6) Creo il comando ChangeColorCommand con la firma corretta:
        //    (Shape shape, Color newStroke, Color newFill, DrawingReceiver receiver)
        ChangeColorCommand command = new ChangeColorCommand(rect, newStroke, newFill, receiver);

        // Act:
        command.execute();

        // Assert:
        // Dopo execute(), la Shape deve riportare i nuovi colori
        assertEquals("Dopo execute, il colore di contorno deve essere RED",
                     newStroke, rect.getPerimetralColor());
        assertEquals("Dopo execute, il colore di riempimento deve essere BLUE",
                     newFill, rect.getInternalColor());
    }

    @Test
    public void undo_ShouldRestoreOriginalColors() {
        // Arrange:
        // 1) Creo una RectangleShape con colori di default (perimetral = BLACK, internal = TRANSPARENT)
        Shape rect = new RectangleShape(0, 0, 10, 10);
        // 2) Inserisco la Shape in una lista per il DrawingReceiver
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);
        // 3) Creo il DrawingReceiver con la lista e un Pane JavaFX
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());
        // 4) Verifico i colori iniziali attesi
        Color originalStroke = rect.getPerimetralColor();
        Color originalFill   = rect.getInternalColor();
        assertEquals("Il colore di contorno iniziale deve essere BLACK",
                     Color.BLACK, originalStroke);
        assertEquals("Il colore di riempimento iniziale deve essere TRANSPARENT",
                     Color.TRANSPARENT, originalFill);

        // 5) Scelgo nuovi colori e creo il comando (notare la firma corretta)
        Color newStroke = Color.GREEN;
        Color newFill   = Color.YELLOW;
        ChangeColorCommand command = new ChangeColorCommand(rect, newStroke, newFill, receiver);
        // 6) Eseguo execute() per cambiare i colori
        command.execute();
        // Verifico che i colori siano effettivamente cambiati
        assertEquals("Dopo execute, il colore di contorno deve essere GREEN",
                     newStroke, rect.getPerimetralColor());
        assertEquals("Dopo execute, il colore di riempimento deve essere YELLOW",
                     newFill, rect.getInternalColor());

        // Act:
        command.undo();

        // Assert:
        // Dopo undo(), la Shape deve aver ripristinato i colori originali
        assertEquals("Dopo undo, il colore di contorno deve tornare BLACK",
                     originalStroke, rect.getPerimetralColor());
        assertEquals("Dopo undo, il colore di riempimento deve tornare TRANSPARENT",
                     originalFill, rect.getInternalColor());
    }
}
