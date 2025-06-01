/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 *
 * @author ciroc
 */
public class IrregularPolygonShape extends Shape implements Serializable{

    
    private transient List<Point2D> polygonPoints = new ArrayList<>();
    

    public IrregularPolygonShape() {
        super(0,0,0,0); 
    }

    public void addPoint(double x, double y) {
        polygonPoints.add(new Point2D(x, y));
    }

    public boolean isClosed() {
        return polygonPoints.size() > 2 && polygonPoints.get(0).distance(polygonPoints.get(polygonPoints.size() - 1)) < 10;
    }

    @Override
    public javafx.scene.shape.Shape toFXShape() {
        Polygon polygon = new Polygon();
        for (Point2D point : polygonPoints) {
            polygon.getPoints().addAll(point.getX(), point.getY());
        }
        computeBoundingBox();
        polygon.setStroke(getPerimetralColor());
        polygon.setFill(getInternalColor());

        applyTransformsToNode(polygon);
        setFXShape(polygon);
        return polygon;
    }

    @Override
    public String getType() {
        return "IRREGULAR_POLYGON";
    }

    @Override
    public Shape clone() {
        IrregularPolygonShape clone = new IrregularPolygonShape();
        for (Point2D point : polygonPoints) {
            clone.addPoint(point.getX(), point.getY());
        }
        clone.initialX = this.initialX;
        clone.initialY = this.initialY;
        clone.finalY = this.finalY;
        clone.finalX = this.finalX;
        clone.setPerimetralColor(this.getPerimetralColor());
        clone.setInternalColor(this.getInternalColor());
        clone.rotation = this.rotation;
        clone.mirrorX  = this.mirrorX;
        clone.mirrorY  = this.mirrorY;
        return clone;
    }

    public List<Point2D> getPolygonPoints() {
        return polygonPoints;
    }

    public void setPolygonPoints(List<Point2D> points) {
        this.polygonPoints = new ArrayList<>(points);
    }
    
    

    
    @Override
    public void moveBy(double dx, double dy) {
        // aggiorna coordinate logiche
        this.initialX += dx;
        this.finalX += dx;
        this.initialY += dy;
        this.finalY += dy;

        // aggiorna il nodo grafico, se presente
        if (fxShape != null) {
            fxShape.setTranslateX(fxShape.getTranslateX() + dx);
            fxShape.setTranslateY(fxShape.getTranslateY() + dy);
        }
        
        List<Point2D> movedPoints = new ArrayList<>();
        for (Point2D p : polygonPoints) {
            movedPoints.add(p.add(dx, dy));
        }
        this.polygonPoints = movedPoints;
    }

    
    
    public void computeBoundingBox() {
        double minX = polygonPoints.stream().mapToDouble(Point2D::getX).min().orElse(0);
        double maxX = polygonPoints.stream().mapToDouble(Point2D::getX).max().orElse(0);
        double minY = polygonPoints.stream().mapToDouble(Point2D::getY).min().orElse(0);
        double maxY = polygonPoints.stream().mapToDouble(Point2D::getY).max().orElse(0);

        this.initialX = minX;
        this.initialY = minY;
        this.finalX = maxX;
        this.finalY = maxY;
    }
    
    
@Override
    public void resize(double newWidth, double newHeight) {
        double oldWidth = getWidth();
        double oldHeight = getHeight();

        if (oldWidth == 0 || oldHeight == 0) return; // evita divisioni per zero

        double centerX = (initialX + finalX) / 2;
        double centerY = (initialY + finalY) / 2;

        double scaleX = newWidth / oldWidth;
        double scaleY = newHeight / oldHeight;

        List<Point2D> resizedPoints = new ArrayList<>();
        for (Point2D p : polygonPoints) {
            double newX = centerX + (p.getX() - centerX) * scaleX;
            double newY = centerY + (p.getY() - centerY) * scaleY;
            resizedPoints.add(new Point2D(newX, newY));
        }

        polygonPoints = resizedPoints;
        computeBoundingBox(); // ricalcola initial/final per coerenza
        updateFXShapeFromLogic();
    }       
    
    public void updateFXShapeFromLogic() {
        Polygon polygon = new Polygon();
        for (Point2D point : polygonPoints) {
            polygon.getPoints().addAll(point.getX(), point.getY());
        }
        polygon.setStroke(getPerimetralColor());
        polygon.setFill(getInternalColor());

        applyTransformsToNode(polygon); // applica rotazione/mirror se servono
        setFXShape(polygon); // aggiorna il nodo associato
    }
    
    //Serializzazione della forma (dato che point2D non è serializzabile)
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        List<SerializablePoint2D> serializableList = polygonPoints.stream()
            .map(SerializablePoint2D::fromFX)
            .collect(Collectors.toList());
        out.writeObject(serializableList);
    }
    
    //Lettura della forma da file serializzato
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        List<SerializablePoint2D> serializableList = (List<SerializablePoint2D>) in.readObject();
        this.polygonPoints = serializableList.stream()
            .map(SerializablePoint2D::toFX)
            .collect(Collectors.toList());
    }
}

