package Shapes;

import javafx.scene.paint.Color;

/**
 * La classe ShapeFactory si occupa della creazione delle varie forme concrete (LineShape,
 * RectangleShape, EllipseShape) a partire da una stringa identificativa. 
 * 
 * È un’applicazione del Factory Design Pattern.
 * 
 * L’obiettivo è elevare il processo di creazione delle forme, in modo
 * che il chiamante non abbia contatto diretto con i dettagli delle singole classi.
 * 
 * @author ciroc
 */
public class ShapeFactory {
    
    /**
     * Metodo per la creazione di una forma concreta senza specificarne i colori di bordo e riempimento
     * 
     * @param selected_shape Il tipo di forma da creare (LINE, RECTANGLE, ELLIPSE)
     * @param x1 Coordinata X iniziale
     * @param y1 Coordinata Y iniziale
     * @param x2 Coordinata X finale
     * @param y2 Coordinata Y finale
     * @return Un oggetto di tipo Shape
     * @throws IllegalArgumentException se la forma specificata non è supportata
     */
    public static Shape createShape(String selected_shape, double x1, double y1, double x2, double y2) {
        return createShape(selected_shape, x1, y1, x2, y2, Color.BLACK, Color.TRANSPARENT, null);
    }
    
    /**
     * Metodo senza specificare il testo (nel caso la forma non fosse una stringa)
     * @param selected_shape Il tipo di forma da creare (LINE, RECTANGLE, ELLIPSE)
     * @param x1 Coordinata X iniziale
     * @param y1 Coordinata Y iniziale
     * @param x2 Coordinata X finale
     * @param y2 Coordinata Y finale
     * @param strokeColor Colore del bordo della figura
     * @param fillColor Colore di riempimento della figura
     * @return 
     */
    public static Shape createShape(String selected_shape, double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor) {
        return createShape(selected_shape, x1, y1, x2, y2, strokeColor, fillColor, null);
    }
    
    /**
     * Metodo per la creazione di una forma concreta specificandone i colori di bordo e riempimento
     * 
     * @param selected_shape Il tipo di forma da creare (LINE, RECTANGLE, ELLIPSE)
     * @param x1 Coordinata X iniziale
     * @param y1 Coordinata Y iniziale
     * @param x2 Coordinata X finale
     * @param y2 Coordinata Y finale
     * @param strokeColor Colore del bordo della figura
     * @param fillColor Colore di riempimento della figura
     * @param textContent testo nel caso la forma fosse una stringa
     * @return Un oggetto di tipo Shape
     * @throws IllegalArgumentException se la forma specificata non è supportata
     */
    public static Shape createShape(String selected_shape, double x1, double y1, double x2, double y2,Color strokeColor, Color fillColor, String textContent) {
        Shape shape;
        
        switch (selected_shape) {
            case "LINE":
                shape = new LineShape(x1, y1, x2, y2);
                break;
            case "RECTANGLE":
                shape = new RectangleShape(x1, y1, x2, y2);
                break;
            case "ELLIPSE":
                shape = new EllipseShape(x1, y1, x2, y2);
                break;
            case "TEXT":
                shape = new TextShape(textContent, x1, y1);
                break;
            case "POLYGON":
                shape = new IrregularPolygonShape();
                break;
            default:
                throw new IllegalArgumentException("Forma inserita, non supportata: " + selected_shape);
        }
        
        // Applica i colori se non nulli
        if (strokeColor != null) shape.setPerimetralColor(strokeColor);
        if (fillColor != null && !selected_shape.equals("LINE")) shape.setInternalColor(fillColor);
        return shape;
    }
    
}
