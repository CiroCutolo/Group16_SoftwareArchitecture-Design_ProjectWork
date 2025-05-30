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
        applyTransformsToNode(line);
        return line;
        
    }
    
    /**
     * Restituisce il tipo della forma come stringa.
     *
     * @return "LINE" come tipo della forma
     */
    @Override
    public String getType(){
        return "LINE";
    }
    
    /**
     * Crea e restituisce una copia della linea corrente.
     * Copia le coordinate e il colore perimetrale, ricreando un nuovo oggetto JavaFX equivalente.
     *
     * @return nuova istanza di LineShape con le stesse proprietà dell'originale
     */
    @Override
    public Shape clone() {      
        LineShape copy = new LineShape(initialX, initialY, finalX, finalY);
        // Copia i colori logici nella nuova istanza
        copy.perimetralColorString = this.perimetralColorString;
        
        copy.rotation = this.rotation;
        copy.mirrorX  = this.mirrorX;
        copy.mirrorY  = this.mirrorY;
        
        Line copyLine = new Line(initialX, initialY, finalX, finalY);
        copyLine.setStroke(Color.valueOf(perimetralColorString));
        copy.applyTransformsToNode(copyLine);
        copy.setFXShape(copyLine);
        return copy;
    }

}
