package Shapes;

import javafx.scene.paint.Color;
import java.io.Serializable;
import javafx.geometry.Bounds;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/**
 * La classe astratta Shape rappresenta una macro-categoria di forme.Introduce
 * il metodo 'toFXShape', in modo da generare correttamente un oggetto di tipo
 * 'Shape' di JavaFX.
 *
 * @author ciroc
 */
public abstract class Shape implements Serializable, Cloneable {

    protected String perimetralColorString = Color.BLACK.toString(); // Viene definito un colore perimetrale di base
    protected String internalColorString = Color.TRANSPARENT.toString(); // Viene definito un colore interno di base
    protected transient javafx.scene.shape.Shape fxShape;
    protected double initialX, initialY, finalX, finalY; //Vengono definite le dimensioni comuni alle forme di inizio e fine pressione
    // ─── NUOVI campi trasformazione ────────────────────
    protected double rotation = 0.0;   // gradi cumulativi
    protected boolean mirrorX = false; // specchio orizzontale
    protected boolean mirrorY = false; // specchio verticale
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
    
    /* --- getters/setters --------------------------------------------------- */
    public double  getRotation() { return rotation; }
    public boolean isMirroredX() { return mirrorX; }
    public boolean isMirroredY() { return mirrorY; }

    /**
     * Metodo astratto che converte l'oggetto Shape in un oggetto Shape di
     * JavaFX. Ogni sottoclasse dovrà implementare questo metodo per generare la
     * forma grafica corrispondente.
     *
     * @return oggetto Shape di JavaFX rappresentante la forma disegnata
     */
    public abstract javafx.scene.shape.Shape toFXShape();

    /**
     * Imposta il colore del bordo (perimetro) della forma.
     *
     * @param new_color nuovo colore da assegnare al bordo
     */
    public void setPerimetralColor(Color new_color) {
        this.perimetralColorString = new_color.toString();
    }

    /**
     * Imposta il colore interno della forma.
     *
     * @param new_color nuovo colore da assegnare all'interno della forma
     */
    public void setInternalColor(Color new_color) {
        this.internalColorString = new_color.toString();
    }

    /**
     * Restituisce il colore del bordo della figura
     *
     * @return colore del bordo della figura come Color
     */
    public Color getPerimetralColor() {
        return Color.valueOf(perimetralColorString);
    }

    /**
     * Restituisce il colore di riempimento della figura
     *
     * @return colore del riempimento della figura come Color
     */
    public Color getInternalColor() {
        return Color.valueOf(internalColorString);
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

    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    public void setInitialY(double initialY) {
        this.initialY = initialY;
    }

    public void setFinalX(double finalX) {
        this.finalX = finalX;
    }

    public void setFinalY(double finalY) {
        this.finalY = finalY;
    }

    
    /**
     * Metodo astratto per ottenere il tipo della forma (es. "RECTANGLE",
     * "LINE"...). Ogni sottoclasse deve fornire il proprio tipo.
     *
     * @return stringa rappresentante il tipo della forma
     */
    public abstract String getType();

    /**
     * Metodo astratto per creare una copia della forma. Ogni sottoclasse deve
     * implementare una logica di clonazione specifica.
     *
     * @return nuova istanza della forma, con le stesse proprietà
     */
    
    @Override
    abstract public Shape clone();

    /**
     * Sposta la forma di una quantità dx, dy sia a livello logico (coordinate
     * iniziali/finali) sia a livello grafico (nodo JavaFX).
     *
     * @param dx spostamento orizzontale
     * @param dy spostamento verticale
     */
    public void moveBy(double dx, double dy) {
        // aggiorna coordinate logiche
        this.initialX += dx;
        this.finalX += dx;
        this.initialY += dy;
        this.finalY += dy;

        // aggiorna il nodo grafico, se presente
        if (fxShape != null) {
            fxShape.setTranslateX(fxShape.getTranslateX() + dx);
            fxShape.setTranslateY(fxShape.getTranslateY() + dy);
        }
    }

    /**
     * Restituisce la larghezza corrente della forma.
     * 
     * Calcola la differenza assoluta tra la coordinata finale e quella iniziale
     * sull'asse X, rappresentando la larghezza della forma indipendentemente
     * dall'orientamento.
     * 
     *
     * @return la larghezza della forma in unità di coordinate (pixel)
     */
    public double getWidth() {
        return Math.abs(finalX - initialX);
    }

    /**
     * Restituisce l'altezza corrente della forma.
     * 
     * Calcola la differenza assoluta tra la coordinata finale e quella iniziale
     * sull'asse Y, rappresentando l'altezza della forma indipendentemente
     * dall'orientamento.
     * 
     *
     * @return l'altezza della forma in unità di coordinate (pixel)
     */
    public double getHeight() {
        return Math.abs(finalY - initialY);
    }

    /**
     * Ridimensiona la forma modificando larghezza e altezza mantenendo fisse le
     * coordinate iniziali.
     * 
     * Aggiorna le coordinate finali calcolandole a partire dalle dimensioni
     * nuove, mantenendo fisse le coordinate iniziali (initialX, initialY).
     * 
     *
     * @param newWidth la nuova larghezza da impostare (deve essere positiva)
     * @param newHeight la nuova altezza da impostare (deve essere positiva)
     */
    public void resize(double newWidth, double newHeight) {
        this.finalX = this.initialX + newWidth;
        this.finalY = this.initialY + newHeight;
    }
    
    /* --- operazioni di “business” ------------------------------------------ */
    public void rotate(double angle) {
        rotation = (rotation + angle) % 360;
        if (fxShape != null) applyTransformsToNode(fxShape);
    }
    public void mirror(boolean horizontal) {
        if (horizontal) mirrorX = !mirrorX;
        else            mirrorY = !mirrorY;
        if (fxShape != null) applyTransformsToNode(fxShape);
    }

    /* --- traduce lo stato logico in trasformazioni JavaFX ------------------ */
    protected void applyTransformsToNode(javafx.scene.shape.Shape node) {
        node.getTransforms().clear();

        // mirror
        double sx = mirrorX ? -1 : 1;
        double sy = mirrorY ? -1 : 1;
        if (mirrorX || mirrorY) {
            Bounds b = node.getBoundsInLocal();
            double cx = (b.getMinX() + b.getMaxX()) / 2;
            double cy = (b.getMinY() + b.getMaxY()) / 2;
            node.getTransforms().add(new Scale(sx, sy, cx, cy));
        }

        // rotazione
        if (rotation != 0) {
            Bounds b = node.getBoundsInLocal();
            double cx = (b.getMinX() + b.getMaxX()) / 2;
            double cy = (b.getMinY() + b.getMaxY()) / 2;
            node.getTransforms().add(new Rotate(rotation, cx, cy));
        }
    }
    
}
