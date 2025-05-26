/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author gaetanof
 */
/**
 * La classe GridHandler gestisce la creazione e la visualizzazione di una
 * griglia su un Pane JavaFX. Permette di mostrare/nascondere la griglia e di
 * modificarne la spaziatura tra le linee.
 */
public class GridHandler {

    // Dimensioni dell'area su cui disegnare la griglia
    private final double width;
    private final double height;

    // Gruppo che contiene tutte le linee della griglia
    private final Group gridGroup;

    // Spaziatura tra le linee della griglia
    private double spacing;

    /**
     * Costruttore della classe GridHandler.
     *
     * @param width La larghezza dell'area della griglia.
     * @param height L'altezza dell'area della griglia.
     * @param spacing La spaziatura iniziale tra le linee della griglia.
     */
    public GridHandler(double width, double height, double spacing) {
        this.width = width;
        this.height = height;
        //this.parentPane = parentPane;
        this.spacing = spacing;
        this.gridGroup = new Group();
        drawGrid(); // Disegna la griglia iniziale
        gridGroup.setVisible(false); // La griglia è inizialmente nascosta
        gridGroup.setMouseTransparent(true); // La griglia ignora gli eventi legati al mouse
        //addToPane(); // Aggiunge la griglia al Pane
    }

    /**
     * Disegna la griglia basata sulle dimensioni e sulla spaziatura attuali.
     * Cancella le linee esistenti e disegna nuove linee verticali e
     * orizzontali.
     */
    private void drawGrid() {
        gridGroup.getChildren().clear();

        // Disegna le linee verticali
        for (double x = 0; x <= width; x += spacing) {
            Line line = new Line(x, 0, x, height);
            line.setStroke(Color.LIGHTGRAY);
            line.setStrokeWidth(0.5);
            gridGroup.getChildren().add(line);
        }

        // Disegna le linee orizzontali
        for (double y = 0; y <= height; y += spacing) {
            Line line = new Line(0, y, width, y);
            line.setStroke(Color.LIGHTGRAY);
            line.setStrokeWidth(0.5);
            gridGroup.getChildren().add(line);
        }
    }

    /**
     * Alterna la visibilità della griglia. Se la griglia è visibile, viene
     * nascosta; se è nascosta, viene mostrata.
     */
    public void toggleVisibility() {
        gridGroup.setVisible(!gridGroup.isVisible());
    }

    /**
     * Restituisce lo stato di visibilità attuale della griglia.
     *
     * @return true se la griglia è visibile, false altrimenti.
     */
    public boolean isVisible() {
        return gridGroup.isVisible();
    }

    /**
     * Imposta una nuova spaziatura tra le linee della griglia e ridisegna la
     * griglia se è attualmente visibile.
     *
     * @param newSpacing La nuova spaziatura da impostare.
     */
    public void setSpacing(double newSpacing) {
        this.spacing = newSpacing;
        if (isVisible()) {
            drawGrid(); // Ridisegna la griglia con la nuova spaziatura
        }
    }

    /**
     * Restituisce il nodo della griglia, utile per operazioni avanzate come la
     * rimozione manuale dal Pane.
     *
     * @return Il nodo Group che rappresenta la griglia.
     */
    public Node getGridNode() {
        return gridGroup;
    }
}
