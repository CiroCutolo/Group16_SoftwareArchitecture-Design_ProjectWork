/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 *
 * @author ciroc
 */
public class IrregularPolygonShape extends Shape {

    private List<Point2D> polygonPoints = new ArrayList<>();

    public IrregularPolygonShape() {
        super(0, 0, 0, 0); 
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
        clone.setPerimetralColor(this.getPerimetralColor());
        clone.setInternalColor(this.getInternalColor());
        return clone;
    }

    public List<Point2D> getPolygonPoints() {
        return polygonPoints;
    }
}

