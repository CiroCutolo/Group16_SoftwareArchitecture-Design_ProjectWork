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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test unitari per BringForwardCommand.
 */
public class BringForwardCommandTest {

    private List<Shape> drawShapes;
    private Pane drawingPane;
    private Shape shape1;
    private Shape shape2;
    private Shape shape3;

    @Before
    public void setUp() {
        new JFXPanel(); 

        drawingPane = new Pane();

        shape1 = new RectangleShape(0, 0, 10, 10);
        shape2 = new RectangleShape(10, 10, 20, 20);
        shape3 = new RectangleShape(20, 20, 30, 30);
        
        javafx.scene.shape.Shape fxShape1 = shape1.toFXShape();
        shape1.setFXShape(fxShape1);
        javafx.scene.shape.Shape fxShape2 = shape2.toFXShape();
        shape2.setFXShape(fxShape2);
        javafx.scene.shape.Shape fxShape3 = shape3.toFXShape();
        shape3.setFXShape(fxShape3);
        
        drawShapes = new ArrayList<>();
        drawShapes.add(shape1);
        drawShapes.add(shape2);
        drawShapes.add(shape3);

        drawingPane.getChildren().addAll(shape1.getFXShape(), shape2.getFXShape(), shape3.getFXShape());
    }

    /**
     * Questo test verifica che una forma situata in un livello intermedio, 
     * venga portata avanti senza problemi.
     */
    @Test
    public void testExecute_MiddleShapeForward() {
        BringForwardCommand command = new BringForwardCommand(shape2, drawShapes, drawingPane);
        command.execute();

        assertSame(shape1, drawShapes.get(0));
        assertSame(shape3, drawShapes.get(1));
        assertSame(shape2, drawShapes.get(2));
    }

    /**
     * Verifica, tramite l'esecuzione della forward sulla shape situata nel livello
     * più alto, che la forma non subisca spostamenti tra i livelli, essendo già
     * nel livello più superficiale.
     */
    @Test
    public void testExecute_LastShapeCreatedToFront() {
        BringForwardCommand command = new BringForwardCommand(shape3, drawShapes, drawingPane);
        command.execute();

        assertEquals(shape1, drawShapes.get(0));
        assertEquals(shape2, drawShapes.get(1));
        assertEquals(shape3, drawShapes.get(2));
    }

    /**
     * Questo test, verifica l'efficacia dell'undo, eseguendolo subito dopo 
     * l'esecuzione di una forward su una forma, riportando la stessa forma
     * al suo livello originale.
     */
    @Test
    public void testUndo_AfterForwarding() {
        BringForwardCommand command = new BringForwardCommand(shape2, drawShapes, drawingPane);
        command.execute();
        command.undo();

        assertEquals(shape1, drawShapes.get(0));
        assertEquals(shape2, drawShapes.get(1));
        assertEquals(shape3, drawShapes.get(2));
    }

    /**
     * Questo test, verifica che l'undo non apporti modifiche rispetto ai livelli,
     * eseguendo l'inversione quando non era stato effettuato alcun comando rispetto alla
     * modifica dei livelli delle forme presenti nel riquadro di disegno.
     */
    @Test
    public void testUndo_WithoutPreviousForwarding() {
        
        for (int i = 0; i < drawShapes.size(); i++) {
            Shape s = drawShapes.get(i);
            System.out.println("Index " + i + ": " + s);
        }
        
        BringForwardCommand command = new BringForwardCommand(shape2, drawShapes, drawingPane);
        command.undo();

        for (int i = 0; i < drawShapes.size(); i++) {
            Shape s = drawShapes.get(i);
            System.out.println("Index " + i + ": " + s);
        }
        
        assertSame(shape1, drawShapes.get(0));
        assertSame(shape2, drawShapes.get(1));
        assertSame(shape3, drawShapes.get(2));
        assertTrue(drawShapes.contains(shape1));
        assertTrue(drawShapes.contains(shape2));
        assertTrue(drawShapes.contains(shape3));
        
        
    }
}