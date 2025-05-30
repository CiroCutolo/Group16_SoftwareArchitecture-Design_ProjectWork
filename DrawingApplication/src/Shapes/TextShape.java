/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shapes;

import java.io.Serializable;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Sterm
 */
public class TextShape extends Shape implements Serializable {

    private String textContent; // Contenuto testuale della forma
    private double fontSize;    // Dimensione del testo

    /**
     * Costruttore per una forma testuale.
     *
     * @param text Il contenuto testuale
     * @param initialX Coordinata X di inserimento del testo
     * @param initialY Coordinata Y di inserimento del testo
     */
    public TextShape(String text, double initialX, double initialY) {
        super(initialX, initialY, initialX,initialY); // finalX/finalY sono inutili qui, ma richiesti dalla superclasse
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



    /**
     * Ridimensionamento: non applicabile direttamente al testo.
     * 
     */
    //(almeno per adesso)
    //Da vedere poi per la stretch se useremo sempre resize o altra funzione
    @Override
    public void resize(double newWidth, double newHeight) {
        // Il testo non viene ridimensionato geometricamente.
        // La dimensione è controllata dal fontSize (vedi setFontSize()).
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
}

