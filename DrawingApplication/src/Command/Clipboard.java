/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;
import Shapes.Shape;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sterm
 */



/**
 * Classe che rappresenta una clipboard personalizzata per la gestione delle forme.
 * Utilizza il Pattern Prototype per clonare le forme copiate e incollate.
 */
public class Clipboard {
    private List<Shape> buffer = new ArrayList<>();

    /**
     * Salva una lista di forme nella clipboard.
     * Le forme vengono clonate per evitare riferimenti diretti all'originale (Prototype Pattern).
     *
     * @param shapes lista di forme da copiare nella clipboard
     */
    public void setContents(List<Shape> shapes) {
        buffer.clear();
        for (Shape shape : shapes) {
            buffer.add(shape.clone()); // Prototype Pattern
        }
    }

    /**
     * Restituisce una nuova lista di cloni delle forme attualmente nella clipboard.
     * 
     * @return lista di forme clonate
     */
    public List<Shape> getContents() {
        List<Shape> clones = new ArrayList<>();
        for (Shape shape : buffer) {
            clones.add(shape.clone());
        }
        return clones;
    }

    /**
     * Verifica se la clipboard è vuota.
     * 
     * @return true se la clipboard è vuota, false altrimenti
     */
    public boolean isEmpty() {
        return buffer.isEmpty();
    }


}
