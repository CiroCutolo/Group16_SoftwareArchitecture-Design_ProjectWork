/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import Shapes.Shape;

import java.io.*;
import java.util.List;
/**
 *
 * @author Sterm
 */

/**
 * La classe ShapeIOManager si occupa della gestione dell'I/O
 * per le forme disegnate, permettendo di salvare e caricare
 * una lista di oggetti Shape tramite file serializzati (.ser).
 *
 * Include interazioni con l'interfaccia grafica tramite FileChooser
 * per selezionare il percorso di salvataggio o caricamento.
 * 
 */
public class ShapeIOManager {

    /**
     * Salva la lista di forme su un file selezionato dall'utente,
     * utilizzando un FileChooser e la serializzazione Java standard.
     *
     * @param shapes lista delle forme da salvare
     * @param ownerWindow finestra principale su cui ancorare il FileChooser
     */
    public void saveShapes(List<Shape> shapes, Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva Disegno");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Disegni serializzati", "*.ser")
        );
        File file = fileChooser.showSaveDialog(ownerWindow);

        if (file != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(shapes);
                System.out.println("Forme salvate: " + shapes.size());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else    System.out.println("Salvataggio cancellato");
    }

    /**
     * Carica da file una lista di forme precedentemente serializzate,
     * utilizzando un FileChooser per selezionare il file .ser.
     *
     * @param ownerWindow finestra principale su cui ancorare il FileChooser
     * @return la lista delle forme caricate dal file, oppure null se il caricamento è annullato
     */
    public List<Shape> loadShapes(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica Disegno");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Disegni serializzati", "*.ser")
        );
        File file = fileChooser.showOpenDialog(ownerWindow);

        if (file != null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<Shape> loaded = (List<Shape>) in.readObject();
                System.out.println("Forme caricate: " + loaded.size());
                return loaded;
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}

