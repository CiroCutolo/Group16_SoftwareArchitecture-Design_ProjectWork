package Shapes;

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
     * Metodo per la creazione di una forma concreta.
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
        switch (selected_shape) {
            case "LINE":
                return new LineShape(x1, y1, x2, y2);
            case "RECTANGLE":
                return new RectangleShape(x1, y1, x2, y2);
            case "ELLIPSE":
                return new EllipseShape(x1, y1, x2, y2);
            default:
                throw new IllegalArgumentException("Forma inserita, non supportata: " + selected_shape);
        }
    }
    
}
