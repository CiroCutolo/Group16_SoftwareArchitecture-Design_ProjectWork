package Shapes;

import javafx.scene.paint.Color;
import java.io.Serializable;
import javafx.scene.Node;
        
/**
 * La classe astratta Shape rappresenta una macro-categoria di forme.Introduce il metodo 'toFXShape', in modo da generare correttamente un oggetto 
 di tipo 'Shape' di JavaFX.
 * 
 * @author ciroc
 */
public abstract class Shape implements Serializable,Cloneable{
    protected String perimetralColorString = Color.BLACK.toString(); // Viene definito un colore perimetrale di base
    protected String internalColorString = Color.TRANSPARENT.toString(); // Viene definito un colore interno di base
    protected transient javafx.scene.shape.Shape fxShape;
    protected double initialX, initialY, finalX, finalY; //Vengono definite le dimensioni comuni alle forme di inizio e fine pressione

    /**
     * Costruttore della forma astratta.
     * 
     * @param initialX coordinata X del punto iniziale (click del mouse)
     * @param initialY Coordinata Y del punto iniziale (click del mouse)
     * @param finalX Coordinata X del punto finale (rilascio del mouse)
     * @param finalY Coordinata Y del punto finale (rilascio del mouse)
     */
    public Shape(double initialX, double initialY, double finalX, double finalY) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.finalX = finalX;
        this.finalY = finalY;
    }
    
    /**
     * Metodo astratto che converte l'oggetto Shape in un oggetto Shape di JavaFX.
     * Ogni sottoclasse dovrà implementare questo metodo per generare la forma grafica corrispondente.
     * 
     * @return oggetto Shape di JavaFX rappresentante la forma disegnata
     */
    public abstract javafx.scene.shape.Shape toFXShape(); 
    
    /**
     * Imposta il colore del bordo (perimetro) della forma.
     * 
     * @param new_color nuovo colore da assegnare al bordo
     */
    public void setPerimetralColor(Color new_color){
        this.perimetralColorString = new_color.toString();
    }
    
    /**
     * Imposta il colore interno della forma.
     * 
     * @param new_color nuovo colore da assegnare all'interno della forma
     */
    public void setInternalColor(Color new_color){ 
        this.internalColorString = new_color.toString();
    }
    

    /**
     * Restituisce l'oggetto fxShape associato a questa forma.
     * 
     * @return l'oggetto JavaFX Shape corrente
     */
    public javafx.scene.shape.Shape getFXShape() {
        return fxShape;
    }

    /**
     * Imposta l'oggetto fxShape per questa forma.
     * 
     * @param fxShape oggetto JavaFX Shape da associare
     */
    public void setFXShape(javafx.scene.shape.Shape fxShape) {
        this.fxShape = fxShape;
    }

    /**
     * Restituisce la coordinata X iniziale della forma.
     * 
     * @return valore X di inizio tracciamento
     */
    public double getInitialX() {
        return initialX;
    }

    /**
     * Restituisce la coordinata Y iniziale della forma.
     * 
     * @return valore Y di inizio tracciamento
     */
    public double getInitialY() {
        return initialY;
    }

    /**
     * Restituisce la coordinata X finale della forma.
     * 
     * @return valore X di fine tracciamento
     */
    public double getFinalX() {
        return finalX;
    }
    
    /**
     * Restituisce la coordinata Y finale della forma.
     * 
     * @return valore Y di fine tracciamento
     */
    public double getFinalY() {
        return finalY;
    }
    
    /**
     * Metodo astratto per ottenere il tipo della forma (es. "RECTANGLE", "LINE"...).
     * Ogni sottoclasse deve fornire il proprio tipo.
     * 
     * @return stringa rappresentante il tipo della forma
     */
    public abstract String getType();
    
    /**
     * Metodo astratto per creare una copia della forma.
     * Ogni sottoclasse deve implementare una logica di clonazione specifica.
     * 
     * @return nuova istanza della forma, con le stesse proprietà
     */
    public abstract Shape clone();
    
    
}
