package Shapes;

import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse; 

/**
 * La classe EllipseShape rappresenta una forma ellittica disegnata dall’utente.
 * Estende la classe astratta 'Shape' e fornisce l’implementazione concreta del metodo
 * 'toFXShape', in modo da generare correttamente un oggetto di tipo 'Ellipse' di JavaFX.
 * 
 * @author ciroc
 */
public class EllipseShape extends Shape implements Serializable{
    
    /**
     * Costruttore della forma ellittica.
     * 
     * @param centerX coordinata X del centro (click del mouse)
     * @param centerY Coordinata Y del centro (click del mouse)
     * @param radiusX Coordinata X del raggio (rilascio del mouse)
     * @param radiusY Coordinata Y del raggio (rilascio del mouse)
     */
    public EllipseShape(double centerX, double centerY, double radiusX, double radiusY) {
        super(centerX, centerY, radiusX, radiusY);
    }

    /**
     * Converte la forma logica in una forma effettiva JavaFX.
     * 
     * @return Un oggetto javafx.scene.shape.Ellièse pronto per essere visualizzato.
     */
    @Override
    public Ellipse toFXShape() {
        
        // Calcolo del centro dell'ellisse
        double centerX = (initialX + finalX) / 2;
        double centerY = (initialY + finalY) / 2;
        
        // Calcolo del raggio verticale e orizzontale dell'ellisse
        double radiusX = Math.abs(finalX - initialX) / 2;
        double radiusY = Math.abs(finalY - initialY) / 2;

        // Creazione della shape JavaFX
        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
        
        // Applicazione delle proprietà visive
        ellipse.setStroke(Color.valueOf(perimetralColorString)); // colore del bordo
        ellipse.setFill(Color.valueOf(internalColorString)); // colore di riempimento
        
        return ellipse;
        
    }

}
