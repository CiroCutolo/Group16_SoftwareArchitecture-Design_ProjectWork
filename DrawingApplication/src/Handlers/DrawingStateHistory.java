/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Command.Command;
import java.util.Stack;

/**
 *
 * @author ciroc
 */
public class DrawingStateHistory {
    private final Stack<Command> history = new Stack<>();
    
    public void executeCommand(Command command){
        command.execute();
        history.push(command);
    }
    
    public void undo(){
        if(!history.isEmpty()){
            Command command = history.pop();
            command.undo();
        }
    }
    
    public boolean isEmpty() {
        return history.isEmpty();
    }
    
    public void push(Command command){
        history.push(command);
    }
}
