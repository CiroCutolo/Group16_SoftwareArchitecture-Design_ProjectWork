/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

/**
 *
 * @author Sterm
 */
public class TextShape extends Shape implements Serializable {

    private String textContent; // Contenuto testuale della forma
    private double fontSize;    // Dimensione del testo
    private double baseX;       //Angolo in basso a sinistra (start della stringa)
    private double baseY;
    private double stretchX = 1;
    private double stretchY = 1;
    private double originalWidth = -1;
    private double originalHeight = -1;
    /**
     * Costruttore per una forma testuale.
     *
     * @param text Il contenuto testuale
     * @param initialX Coordinata X di inserimento del testo
     * @param initialY Coordinata Y di inserimento del testo
     */
    public TextShape(String text, double initialX, double initialY) {
        super(initialX, initialY,0,0);
        this.textContent = text;
        this.fontSize = 20.0; // default
    }

    /**
     * Converte la forma logica in una forma JavaFX concreta.
     *
     * @return Un oggetto javafx.scene.text.Text pronto per il rendering
     */
    @Override
    public Text toFXShape() {
        Text text = new Text(initialX, initialY, textContent);
        text.setFont(Font.font(fontSize));
        text.setStroke(Color.valueOf(perimetralColorString)); // colore del bordo
        text.setFill(Color.valueOf(internalColorString));     // colore di riempimento
        applyTransformsToNode(text);
        return text;
        
    }

    /**
     * Restituisce il tipo della forma come stringa.
     *
     * @return "TEXT" come tipo della forma
     */
    @Override
    public String getType() {
        return "TEXT";
    }

    /**
     * Crea e restituisce una copia della forma testuale corrente.
     *
     * @return nuova istanza di TextShape con le stesse proprietà
     */
    @Override
    public Shape clone() {
        TextShape copy = new TextShape(this.textContent, this.initialX, this.initialY);
        copy.fontSize = this.fontSize;
        copy.perimetralColorString = this.perimetralColorString;
        copy.internalColorString = this.internalColorString;
        copy.baseX = baseX;
        copy.baseY = baseY;
        copy.finalX = finalX;
        copy.finalY = finalY;
        copy.stretchX = stretchX;
        copy.stretchY = stretchY;
        copy.originalWidth = originalWidth;
        copy.originalHeight = originalHeight;
        copy.rotation = this.rotation;
        copy.mirrorX  = this.mirrorX;
        copy.mirrorY  = this.mirrorY;

        Text fxText = new Text(initialX, initialY, textContent);
        fxText.setFont(Font.font(fontSize));
        fxText.setFill(Color.valueOf(internalColorString));
        fxText.setStroke(Color.valueOf(perimetralColorString));
        copy.applyTransformsToNode(fxText);
        copy.setFXShape(fxText);

        return copy;
    }




    // Getter e Setter per il contenuto e la dimensione del testo
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String newText) {
        this.textContent = newText;
        if (fxShape instanceof Text) {
            Text textNode = (Text) fxShape;
            textNode.setText(newText);
        }
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double size) {
        this.fontSize = size;
        if (fxShape instanceof Text){
            Text textNode = (Text) fxShape;
            textNode.setFont(Font.font(size));
        }
    }

    public double getBaseX() {
        return baseX;
    }

    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }

    public double getBaseY() {
        return baseY;
    }

    public void setBaseY(double baseY) {
        this.baseY = baseY;
    }
    
    public double getOriginalWidth() {
        return originalWidth;
    }
    
    public double getOriginalHeight() {
        return originalHeight;
    }
    
    public void setStretchX(double stretchX) {
        this.stretchX = stretchX;
    }
    
    public void setStretchY(double stretchY) {
        this.stretchY = stretchY;
    }
    
    public void checkHeight(){
        double baseline = initialY;
        double topline = baseline - fxShape.getBaselineOffset();
        //Sposta in giù se sfora
        if(topline < 0) {
            double newBaseline = baseline - topline + 15;
            setInitialY(newBaseline);
            Text fxText = (Text) fxShape;
            fxText.setY(newBaseline);
        }
    }
    
    @Override
    public double getWidth() {
        return Math.abs(finalX - baseX);
    }
    @Override
    public double getHeight() {
        return Math.abs(baseY - finalY);
    }
    
    public void computeBoundingBox(Text textShape) {
        Bounds bounds = textShape.localToParent(textShape.getLayoutBounds());
        this.baseX = bounds.getMinX();
        this.baseY = bounds.getMaxY();
        this.finalX = bounds.getMaxX();
        this.finalY = bounds.getMinY();

        if (originalWidth < 0 || originalHeight < 0) {
            this.originalWidth = bounds.getWidth();
            this.originalHeight = bounds.getHeight();
        }
    }



    /* --------------------------------------------------------------------- */
    /** Ricompone scala → mirror → rotazione usando il centro attuale. */
    protected void applyTransformsToNode(javafx.scene.shape.Shape node) {
        node.getTransforms().clear();

        Bounds b = node.getLayoutBounds();
        double cx = (b.getMinX() + b.getMaxX()) / 2.0;
        double cy = (b.getMinY() + b.getMaxY()) / 2.0;

        // ─── Fattori di scala coerenti con la rotazione ───
        double scaleX = stretchX;
        double scaleY = stretchY;

        // Se la rotazione è "verticale" (90° o 270°), scambiare gli assi
        if ((rotation % 180) != 0) {
            double tmp = scaleX;
            scaleX = scaleY;
            scaleY = tmp;
        }
    
        //Trasformazione temporanea utile al calcolo del bounding box (viene poi eliminata)
        if (stretchX != 1 || stretchY != 1)
                node.getTransforms().add(new Scale(stretchX, stretchY, cx, cy));
            // Calcola bounding box senza rotazione e specchiatura
            computeBoundingBox((Text) node);
            node.getTransforms().clear();
        
        
        //Trasformazione (stretch) effettivamente applicata
        if (scaleX != 1 || scaleY != 1)
            node.getTransforms().add(new Scale(scaleX, scaleY, cx, cy));
        
        // ─── rotazione ───────── (non specchio!)
        if (rotation != 0)
            node.getTransforms().add(new Rotate(rotation, cx, cy));

        // ─── mirror (dopo bounding box) ───
        if (mirrorX || mirrorY)
            node.getTransforms().add(
                new Scale(mirrorX ? -1 : 1, mirrorY ? -1 : 1, cx, cy));
    }
    
    
}