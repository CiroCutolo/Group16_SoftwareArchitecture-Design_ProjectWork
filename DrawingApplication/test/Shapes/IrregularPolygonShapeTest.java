/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe di test per IrregularPolygonShape.
 * Verifica la corretta gestione dei punti, la chiusura del poligono,
 * la conversione in forma JavaFX, la clonazione e l'integrità dei dati.
 * 
 * @author gaetanof
 */
public class IrregularPolygonShapeTest {
    
    private IrregularPolygonShape instance;
    
    public IrregularPolygonShapeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new IrregularPolygonShape();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test del metodo addPoint: verifica che i punti vengano aggiunti correttamente.
     */
    @Test
    public void testAddPoint() {
        System.out.println("addPoint");
        // Aggiunge un punto e verifica che sia stato salvato correttamente
        instance.addPoint(10.0, 20.0);
        List<Point2D> points = instance.getPolygonPoints();
        assertEquals(1, points.size());
        assertEquals(10.0, points.get(0).getX(), 0.001);
        assertEquals(20.0, points.get(0).getY(), 0.001);
        
        // Aggiunge un secondo punto e verifica entrambi
        instance.addPoint(30.0, 40.0);
        points = instance.getPolygonPoints();
        assertEquals(2, points.size());
        assertEquals(30.0, points.get(1).getX(), 0.001);
        assertEquals(40.0, points.get(1).getY(), 0.001);
    }

    /**
     * Test del metodo isClosed: verifica la logica di chiusura del poligono.
     */
    @Test
    public void testIsClosed() {
        System.out.println("isClosed");
        
        // Con meno di 3 punti, il poligono non deve essere chiuso
        instance.addPoint(0.0, 0.0);
        instance.addPoint(10.0, 0.0);
        assertFalse(instance.isClosed());
        
        // Con 3 punti ma non vicini, ancora non chiuso
        instance.addPoint(10.0, 10.0);
        assertFalse(instance.isClosed());
        
        // Aggiunge un punto vicino all'inizio: il poligono dovrebbe considerarsi chiuso
        instance.addPoint(0.5, 0.5); // vicino al punto iniziale (0,0)
        assertTrue(instance.isClosed());
    }

    /**
     * Test del metodo toFXShape: verifica la conversione in oggetto Shape JavaFX.
     */
    @Test
    public void testToFXShape() {
        System.out.println("toFXShape");
        
        // Aggiunge 3 punti per formare un triangolo
        instance.addPoint(0.0, 0.0);
        instance.addPoint(100.0, 0.0);
        instance.addPoint(50.0, 100.0);
        
        // Imposta i colori
        instance.setPerimetralColor(Color.BLACK);
        instance.setInternalColor(Color.RED);
        
        // Converte in Shape JavaFX
        javafx.scene.shape.Shape result = instance.toFXShape();
        
        // Verifica che sia un oggetto di tipo Polygon
        assertTrue(result instanceof Polygon);
        
        // Verifica che il poligono abbia il numero corretto di coordinate
        Polygon polygon = (Polygon) result;
        assertEquals(6, polygon.getPoints().size()); // 3 punti * 2 coordinate (x, y) ciascuno
        
        // Verifica i colori
        assertEquals(Color.BLACK, polygon.getStroke());
        assertEquals(Color.RED, polygon.getFill());
    }

    /**
     * Test del metodo getType: verifica il tipo restituito dalla forma.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        String expResult = "IRREGULAR_POLYGON";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test del metodo clone: verifica che la clonazione produca un nuovo oggetto
     * con stessi dati ma indipendente dall'originale.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        
        // Imposta alcuni punti e colori nella forma originale
        instance.addPoint(0.0, 0.0);
        instance.addPoint(100.0, 0.0);
        instance.addPoint(50.0, 100.0);
        instance.setPerimetralColor(Color.BLUE);
        instance.setInternalColor(Color.GREEN);
        
        // Esegue la clonazione
        Shapes.Shape cloneResult = instance.clone();
        
        // Verifica il tipo della forma clonata
        assertTrue(cloneResult instanceof IrregularPolygonShape);
        IrregularPolygonShape clonedShape = (IrregularPolygonShape) cloneResult;
        
        // Verifica che i punti siano stati copiati correttamente
        List<Point2D> originalPoints = instance.getPolygonPoints();
        List<Point2D> clonedPoints = clonedShape.getPolygonPoints();
        assertEquals(originalPoints.size(), clonedPoints.size());
        for (int i = 0; i < originalPoints.size(); i++) {
            assertEquals(originalPoints.get(i).getX(), clonedPoints.get(i).getX(), 0.001);
            assertEquals(originalPoints.get(i).getY(), clonedPoints.get(i).getY(), 0.001);
        }
        
        // Verifica che i colori siano stati copiati correttamente
        assertEquals(instance.getPerimetralColor(), clonedShape.getPerimetralColor());
        assertEquals(instance.getInternalColor(), clonedShape.getInternalColor());
    }

    /**
     * Test del metodo getPolygonPoints: verifica il corretto recupero dei punti.
     */
    @Test
    public void testGetPolygonPoints() {
        System.out.println("getPolygonPoints");
        
        // Inizialmente la lista dei punti deve essere vuota
        List<Point2D> points = instance.getPolygonPoints();
        assertNotNull(points);
        assertTrue(points.isEmpty());
        
        // Aggiunge alcuni punti e verifica che vengano recuperati correttamente
        instance.addPoint(10.0, 20.0);
        instance.addPoint(30.0, 40.0);
        
        points = instance.getPolygonPoints();
        assertEquals(2, points.size());
        assertEquals(10.0, points.get(0).getX(), 0.001);
        assertEquals(20.0, points.get(0).getY(), 0.001);
        assertEquals(30.0, points.get(1).getX(), 0.001);
        assertEquals(40.0, points.get(1).getY(), 0.001);
    }
}
