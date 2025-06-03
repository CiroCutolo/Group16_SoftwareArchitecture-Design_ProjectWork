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
 *   1) execute() ruoti correttamente la Shape di un angolo specificato,
 *   2) undo() ripristini la rotazione originale (cioè ruoti di -angle).
 *
 * Si assume che:
 *   • RectangleShape abbia di default rotation = 0.
 *   • RotateCommand abbia la firma: RotateCommand(Shape shape, double angle, DrawingReceiver receiver).
 *   • execute() invochi receiver.rotateShape(shape, angle), il quale chiama
 *       shape.setRotation((shape.getRotation() + angle) % 360).
 *   • undo() invochi receiver.rotateShape(shape, -angle).
 */
public class RotateCommandTest {
    
    public RotateCommandTest() {
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
    public void execute_ShouldRotateShapeByGivenAngle() {
        // Arrange:
        // 1) Creo una RectangleShape e mi aspetto rotazione iniziale = 0
        Shape rect = new RectangleShape(0, 0, 10, 10);
        assertEquals("Rotazione di default deve essere 0", 0.0, rect.getRotation(), 0.0);

        // 2) Aggiungo la shape a un drawList e creo il DrawingReceiver (serve un Pane JavaFX)
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 3) Definisco un angolo di rotazione, ad esempio 45 gradi
        double angle = 45.0;

        // 4) Istanzio il RotateCommand con la firma corretta
        RotateCommand command = new RotateCommand(rect, angle, receiver);

        // Act:
        command.execute();

        // Assert:
        // Dopo execute(), il metodo rotateShape avrà chiamato shape.setRotation((0 + 45) % 360) = 45
        assertEquals("Dopo execute, rotation deve essere 45 gradi",
                     45.0, rect.getRotation(), 0.0);

        // 5) Se eseguo di nuovo execute() (rotazione aggiuntiva), la rotazione diventa (45 + 45) % 360 = 90
        command.execute();
        assertEquals("Seconda esecuzione: rotation deve essere (45 + 45) % 360 = 90",
                     90.0, rect.getRotation(), 0.0);
    }

    @Test
    public void undo_ShouldRestoreOriginalRotation() {
        // Arrange:
        // 1) Creo una RectangleShape e mi aspetto rotazione iniziale = 0
        Shape rect = new RectangleShape(0, 0, 10, 10);
        assertEquals("Rotazione di default deve essere 0", 0.0, rect.getRotation(), 0.0);

        // 2) Aggiungo la shape a un drawList e creo il DrawingReceiver
        List<Shape> drawList = new ArrayList<>();
        drawList.add(rect);
        DrawingReceiver receiver = new DrawingReceiver(drawList, new Pane());

        // 3) Definisco un angolo di rotazione (ad esempio 100°) e creo il comando
        double angle = 100.0;
        RotateCommand command = new RotateCommand(rect, angle, receiver);

        // 4) Eseguo execute() per ruotare la shape di 100°; ora rotation == 100
        command.execute();
        assertEquals("Dopo execute, rotation deve essere 100°",
                     100.0, rect.getRotation(), 0.0);

        // Act:
        command.undo();

        // Assert:
        // Undo invoca receiver.rotateShape(shape, -100), quindi la rotazione diventa (100 + (-100)) % 360 = 0
        assertEquals("Dopo undo, rotation deve essere tornato a 0",
                     0.0, rect.getRotation(), 0.0);

        // 5) Verifico che se ripeto execute() + undo() con un angolo superiore a 360,
        //    il modulo 360 venga rispettato. Ad esempio angle2 = 370°:
        double angle2 = 370.0;
        // in questo caso, (0 + 370) % 360 = 10
        RotateCommand command2 = new RotateCommand(rect, angle2, receiver);
        command2.execute();
        assertEquals("Dopo execute con 370°, rotation deve essere (0 + 370) % 360 = 10",
                     10.0, rect.getRotation(), 0.0);
        command2.undo();
        // Undo applica rotateShape(shape, -370): (10 + (-370)) % 360 = -360 % 360 = 0
        assertEquals("Dopo undo, rotation deve essere tornato a 0",
                     0.0, rect.getRotation(), 0.0);
    }
}
