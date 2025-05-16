package Shapes;

import javafx.scene.paint.Color;

/**
 * La classe astratta Shape rappresenta una macro-categoria di forme.Introduce il metodo 'toFXShape', in modo da generare correttamente un oggetto 
 di tipo 'Shape' di JavaFX.
 * 
 * @author ciroc
 */
public abstract class Shape {
    
    protected Color perimetralColor = Color.BLACK; // Viene definito un colore perimetrale di base
    protected Color internalColor = Color.TRANSPARENT; // Viene definito un colore interno di base
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
    
    // Metodo astratto per convertire la forma logica in una shape
    public abstract javafx.scene.shape.Shape toFXShape(); 
    
    // Metodo dedito alla modifica del colore perimetrale della forma
    public void setPerimetralColor(Color new_color){
        this.perimetralColor = new_color;
    }
    
    // Modifica dedito alla modifica del colore interno alla forma
    public void setInternalColor(Color new_color){
        this.internalColor = new_color; 
    }
}
