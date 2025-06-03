package Command;

import Shapes.Shape;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Collections;
import java.util.stream.Collectors;
import Shapes.IrregularPolygonShape;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

/**
 * Classe Receiver nel pattern Command.
 * Incapsula tutta la logica di business per manipolare le forme nell'applicazione di disegno.
 * 
 * @author gaetanof
 */
public class DrawingReceiver {
    private final List<Shape> drawShapes;
    private final Pane drawingPane;
    
    /**
     * Costruttore della classe DrawingReceiver.
     * 
     * @param drawShapes lista delle forme attualmente disegnate
     * @param drawingPane pannello JavaFX su cui disegnare le nuove forme
     */
    public DrawingReceiver(List<Shape> drawShapes, Pane drawingPane) {
        this.drawShapes = drawShapes;
        this.drawingPane = drawingPane;
    }

    public Pane getDrawingPane() {
        return drawingPane;
    }

    public List<Shape> getDrawShapes() {
        return drawShapes;
    }
    
    //************************************/
    // Metodi di manipolazione delle forme
    //************************************/

    /**
     * Inserisce una forma nel pannello di disegno e nella lista delle forme.
     * 
     * @param shape la forma da inserire
     */
    public void insertShape(Shape shape) {
        javafx.scene.shape.Shape fxShape = shape.toFXShape();
        shape.setFXShape(fxShape);
        drawShapes.add(shape);
        drawingPane.getChildren().add(fxShape);
    }

    /**
     * Rimuove una forma dalla lista delle forme e dal pannello di disegno.
     * 
     * @param shape la forma da rimuovere
     */
    public void removeShape(Shape shape) {
        drawShapes.remove(shape);
        drawingPane.getChildren().remove(shape.getFXShape());
    }

    /**
     * Cambia il colore della forma.
     * 
     * @param shape la forma da modificare
     * @param newStroke il nuovo colore perimetrale
     */
    public void changeShapeColor(Shape shape, Color newStroke, Color newFill) {
        shape.setPerimetralColor(newStroke);
        shape.setInternalColor(newFill);
        updateShapeVisual(shape);
    }
    
    /**
     * Cambia le dimensioni della forma.
     * 
     * @param shape la forma da modificare
     * @param width la nuova larghezza
     * @param height la nuova altezza
     */
    public void resizeShape(Shape shape, double width, double height) {
        if (shape instanceof IrregularPolygonShape) {
            IrregularPolygonShape polygon = (IrregularPolygonShape) shape;
            double oldWidth = shape.getWidth();
            double oldHeight = shape.getHeight();

            if (oldWidth == 0 || oldHeight == 0) return; // evita divisioni per zero

            double centerX = (shape.getInitialX() + shape.getFinalX()) / 2;
            double centerY = (shape.getInitialY() + shape.getFinalY()) / 2;

            double scaleX = width / oldWidth;
            double scaleY = height / oldHeight;

            List<Point2D> resizedPoints = new ArrayList<>();
            for (Point2D p : polygon.getPolygonPoints()) {
                double newX = centerX + (p.getX() - centerX) * scaleX;
                double newY = centerY + (p.getY() - centerY) * scaleY;
                resizedPoints.add(new Point2D(newX, newY));
            }

            polygon.setPolygonPoints(resizedPoints);
            polygon.computeBoundingBox();
            
            // Update the existing polygon points instead of creating a new shape
            Polygon fxPolygon = (Polygon) shape.getFXShape();
            if (fxPolygon != null) {
                fxPolygon.getPoints().clear();
                for (Point2D point : resizedPoints) {
                    fxPolygon.getPoints().addAll(point.getX(), point.getY());
                }
                fxPolygon.setStroke(shape.getPerimetralColor());
                fxPolygon.setFill(shape.getInternalColor());
            }
        } else {
            // Aggiorna le coordinate logiche
            shape.setFinalX(shape.getInitialX() + width);
            shape.setFinalY(shape.getInitialY() + height);
            updateShapeVisual(shape);
        }
    }
    
    /**
     * Ruota la forma di un angolo specificato.
     * 
     * @param shape la forma da ruotare
     * @param angle l'angolo di rotazione
     */
    public void rotateShape(Shape shape, double angle) {
        shape.setRotation((shape.getRotation() + angle) % 360);
        redrawShapes();
    }
    
    /**
     * Specchia la forma orizzontalmente o verticalmente.
     * 
     * @param shape la forma da specchiare
     * @param horizontal true se specchiare orizzontalmente, false se verticalmente
     */
    public void mirrorShape(Shape shape, boolean horizontal) {
        if (horizontal) {
            shape.setMirrorX(!shape.isMirroredX());
        } else {
            shape.setMirrorY(!shape.isMirroredY());
        }
        //updateShapeVisual(shape);
        redrawShapes();
    }
    
    /**
     * Sposta la forma di una quantità specificata.
     * 
     * @param shape la forma da spostare
     * @param dx la quantità di spostamento in x
     * @param dy la quantità di spostamento in y
     */
    public void moveShape(Shape shape, double dx, double dy) {
        if (shape instanceof IrregularPolygonShape) {
            IrregularPolygonShape polygon = (IrregularPolygonShape) shape;
            List<Point2D> movedPoints = new ArrayList<>();
            for (Point2D p : polygon.getPolygonPoints()) {
                movedPoints.add(p.add(dx, dy));
            }
            polygon.setPolygonPoints(movedPoints);
            polygon.computeBoundingBox();
        }
        
        // Update logical coordinates for all shapes
        shape.setInitialX(shape.getInitialX() + dx);
        shape.setInitialY(shape.getInitialY() + dy);
        shape.setFinalX(shape.getFinalX() + dx);
        shape.setFinalY(shape.getFinalY() + dy);
        
        // Update visual representation for all shapes
        if (shape.getFXShape() != null) {
            shape.getFXShape().setTranslateX(shape.getFXShape().getTranslateX() + dx);
            shape.getFXShape().setTranslateY(shape.getFXShape().getTranslateY() + dy);
        }
    }
    
    //************************************/
    // Metodi di manipolazione dei livelli
    //************************************/

    /**
     * Sposta la forma all'inizio della lista.
     * 
     * @param shape la forma da spostare
     */
    public void bringToFront(Shape shape) {
        drawShapes.remove(shape);
        drawShapes.add(shape);
        redrawShapes();
    }

    /**
     * Sposta la forma all'inizio della lista.
     * 
     * @param shape la forma da spostare
     */
    public void sendToBack(Shape shape) {
        drawShapes.remove(shape);
        drawShapes.add(0, shape);
        redrawShapes();
    }

    /**
     * Sposta la forma in avanti nella lista.
     * 
     * @param shape la forma da spostare
     */
    public void bringForward(Shape shape) {
        int index = drawShapes.indexOf(shape);
        if (index < drawShapes.size() - 1) {
            Collections.swap(drawShapes, index, index + 1);
            redrawShapes();
        }
    }

    /**
     * Sposta la forma indietro nella lista.
     * 
     * @param shape la forma da spostare
     */
    public void sendBackward(Shape shape) {
        int index = drawShapes.indexOf(shape);
        if (index > 0) {
            Collections.swap(drawShapes, index, index - 1);
            redrawShapes();
        }
    }
    
    //************************************/
    // Clipboard operations
    public void cutShape(Shape shape, Clipboard clipboard) {
        clipboard.setContents(Collections.singletonList(shape));
        removeShape(shape);
    }

    /**
     * Copia la forma nel clipboard.
     * 
     * @param shape la forma da copiare
     * @param clipboard il clipboard
     */
    public void copyShape(Shape shape, Clipboard clipboard) {
        clipboard.setContents(Collections.singletonList(shape));
    }

    /**
     * Incolla la forma dal clipboard.
     * 
     * @param clipboard il clipboard
     * @param x la coordinata x in cui incollare la forma
     * @param y la coordinata y in cui incollare la forma
     */
    public void pasteShape(Clipboard clipboard, double x, double y) {
        List<Shape> contents = clipboard.getContents();
        if (contents != null && !contents.isEmpty()) {
            for (Shape shape : contents) {
                try {
                    Shape clone = (Shape) shape.clone();
                    double dx = x - shape.getInitialX();
                    double dy = y - shape.getInitialY();
                    clone.setInitialX(x);
                    clone.setInitialY(y);
                    clone.setFinalX(clone.getFinalX() + dx);
                    clone.setFinalY(clone.getFinalY() + dy);
                    insertShape(clone);
                } catch (Exception e) {
                    System.err.println("Error while pasting shape: " + e.getMessage());
                }
            }
        }
    }
    
    //************************************/
    // Helper methods
    //************************************/

    /**
     * Aggiorna la rappresentazione visiva della forma.
     * 
     * @param shape la forma da aggiornare
     */
    private void updateShapeVisual(Shape shape) {
        if (shape.getFXShape() != null) {
            // Remove old visual
            drawingPane.getChildren().remove(shape.getFXShape());
            // Create and add new visual
            javafx.scene.shape.Shape newFXShape = shape.toFXShape();
            shape.setFXShape(newFXShape);
            drawingPane.getChildren().add(newFXShape);
        }
    }

    /**
     * Ridisegna le forme.
     */
    private void redrawShapes() {
        drawingPane.getChildren().removeIf(node -> !(node instanceof Group));
        drawingPane.getChildren().addAll(
            drawShapes.stream()
                .map(shape -> {
                    javafx.scene.shape.Shape fxShape = shape.toFXShape();
                    shape.setFXShape(fxShape);
                    return fxShape;
                })
                .collect(Collectors.toList())
        );
    }
} 