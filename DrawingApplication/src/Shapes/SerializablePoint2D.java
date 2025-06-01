/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shapes;

import java.io.Serializable;
import javafx.geometry.Point2D;

/**
 *
 * @author Sterm
 */


public class SerializablePoint2D implements Serializable {
    public double x, y;

    public SerializablePoint2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D toFX() {
        return new Point2D(x, y);
    }

    public static SerializablePoint2D fromFX(Point2D p) {
        return new SerializablePoint2D(p.getX(), p.getY());
    }
}

