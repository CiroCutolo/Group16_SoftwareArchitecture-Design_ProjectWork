package Shapes;

import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * La classe LineShape rappresenta una forma rettangolare disegnata dall’utente.
 * Estende la classe astratta 'Shape' e fornisce l’implementazione concreta del metodo
 * 'toFXShape', in modo da generare correttamente un oggetto di tipo 'Line' di JavaFX.
 * 
 * @author ciroc
 */
public class LineShape extends Shape implements Serializable{
    
    /**
     * Costruttore della forma lineare.
     * 
     * @param initialX coordinata X del punto iniziale (click del mouse)
     * @param initialY Coordinata Y del punto iniziale (click del mouse)
     * @param finalX Coordinata X del punto finale (rilascio del mouse)
     * @param finalY Coordinata Y del punto finale (rilascio del mouse)
     */
    public LineShape(double initialX, double initialY, double finalX, double finalY) {
        super(initialX, initialY, finalX, finalY);
    }

    /**
     * Converte la forma logica in una forma effettiva JavaFX.
     * 
     * @return Un oggetto javafx.scene.shape.Line pronto per essere visualizzato.
     */
    @Override
    public Line toFXShape() {
        
        // Creazione della shape JavaFX
        Line line = new Line(initialX, initialY, finalX, finalY);
        
        // Applicazione delle proprietà visive
        line.setStroke(Color.valueOf(perimetralColorString)); // colore del bordo
        
        return line;
        
    }
    
    @Override
    public Node getNode() {
        Line line = new Line(initialX, initialY, finalX, finalY);
        line.setStroke(Color.valueOf(perimetralColorString));
        return line;
    }
    
}
