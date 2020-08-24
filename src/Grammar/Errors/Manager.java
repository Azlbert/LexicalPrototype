/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Errors;

import Grammar.Tokens.Instance;
import java.util.LinkedList;

/**
 *
 * @author Alberto
 */
public class Manager {
    private static Manager instance;
    private LinkedList<Reported> errors;
    
    static public Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }
        
        return instance;
    }
    
    private Manager(){
        errors = new LinkedList<>();
    }
    
    public boolean hasErrors(){
        return errors.size() > 0;
    }
    
    public void printStatus(){
        System.out.println("Encontrados " + errors.size() + " errores");
        if(!hasErrors()){
            System.out.println("Se compilo exitosamente");
            return;
        }
        for(Reported reported : errors){
            System.out.println(reported.getError() + " - " + reported.getInstance().getValue() + " - " + reported.getError().getMsg());
        }
    }
    
    public void reportError(Error error, Instance instance){
        errors.add(new Reported(error, instance));
    }
}
