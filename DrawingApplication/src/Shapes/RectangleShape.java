package Shapes;

import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * La classe RectangleShape rappresenta una forma rettangolare disegnata dall’utente.
 * Estende la classe astratta 'Shape' e fornisce l’implementazione concreta del metodo
 * 'toFXShape', in modo da generare correttamente un oggetto di tipo 'Rectangle' di JavaFX.
 * 
 * @author ciroc
 */
public class RectangleShape extends Shape implements Serializable{
    
    /**
     * Costruttore della forma rettangolare.
     * 
     * @param initialX Coordinata X iniziale (click del mouse)
     * @param initialY Coordinata Y iniziale (click del mouse)
     * @param finalX Coordinata X finale (rilascio del mouse)
     * @param finalY Coordinata Y finale (rilascio del mouse)
     */
    public RectangleShape(double initialX, double initialY, double finalX, double finalY) {
        super(initialX, initialY, finalX, finalY);
    }

    /**
     * Converte la forma logica in una forma effettiva JavaFX.
     * 
     * @return Un oggetto javafx.scene.shape.Rectangle pronto per essere visualizzato.
     */
    @Override
    public Rectangle toFXShape() {
        // Calcolo del vertice superiore sinistro
        double angleX = Math.min(initialX, finalX);
        double angleY = Math.min(initialY, finalY);
        
        // Calcolo di larghezza e altezza del rettangolo
        double rec_width = Math.abs(finalX - initialX);
        double rec_height = Math.abs(finalY - initialY);

        // Creazione della shape JavaFX
        Rectangle rectangle = new Rectangle(angleX, angleY, rec_width, rec_height);
        
        // Applicazione delle proprietà visive
        rectangle.setStroke(Color.valueOf(perimetralColorString)); // colore del bordo
        rectangle.setFill(Color.valueOf(internalColorString));     // colore di riempimento
        
        return rectangle;
    }


}
