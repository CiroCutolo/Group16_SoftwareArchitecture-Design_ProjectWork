/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Command;

/**
 *
 * @author Sterm
 */



/**
 * Interfaccia per il pattern Command.
 * Ogni comando concreto (es. copia, incolla, elimina) deve implementare questo metodo.
 * Permette di incapsulare un'azione come oggetto, facilitando undo/redo e la gestione delle operazioni utente.
 */
public interface Command {
    /**
     * Metodo da implementare per eseguire il comando.
     */
    void execute();
    void undo();
}
