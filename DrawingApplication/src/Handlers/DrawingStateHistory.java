/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Command.Command;
import java.util.Stack;

/**
 * Questa classe definisce la cronologia di comandi, che rappresentato i vari
 * stati del disegno.
 * 
 * @author ciroc
 */
public class DrawingStateHistory {
    private final Stack<Command> history = new Stack<>();
    
    /**
     * Questo metodo serve a inserire nella cronologia di comandi, un nuovo comando 
     * eseguito.
     * 
     * @param command 
     */
    public void executeCommand(Command command) {
        if (command != null) {
            command.execute();
            history.push(command);
        }
    }
    
    /**
     * Questo metodo serve a rimuovere dalla cronologia di comandi, l'ultimo comando
     * inserito, successivamente all'esecuzione della undo.
     * 
     * @return Comando da annullare, o null se la cronologia è vuota
     */
    public Command undo() {
        if (history.isEmpty()) {
            return null;
        }
        Command cmd = history.pop();
        cmd.undo();
        return cmd;
    }
    
    /**
     * Controlla se la cronologia di comandi è vuota.
     * 
     * @return true se la cronologia è vuota, false altrimenti
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }
    
    public void push(Command command){
        history.push(command);
    }
}
