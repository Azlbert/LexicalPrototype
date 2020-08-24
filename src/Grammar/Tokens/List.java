/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Tokens;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Usuario
 */
public class List {
    private static List instance;
    private Queue<Instance> tokens = new LinkedList();
    
    public static List getInstance(){
        if(instance == null){
            instance = new List();
        }
        
        return instance;
    }
    
    private List(){}
    
    public void addInstance(Instance tokenInstance){
        if(tokenInstance != null){
            tokens.add(tokenInstance);
            //System.out.println("Added " + tokenInstance);
        }
    }
    
    public void printList(){
        for(Instance instance : tokens){
            System.out.println(instance);
        }
    }
}
