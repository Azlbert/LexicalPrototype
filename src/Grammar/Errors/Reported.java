/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Errors;

import Grammar.Tokens.Instance;

/**
 *
 * @author Alberto
 */
public class Reported {
    private Error error;
    private Instance instance;
    
    private Reported(){}
    public Reported(Error error, Instance instance){
        this.error = error;
        this.instance = instance;
    }

    public Error getError() {
        return error;
    }

    public Instance getInstance() {
        return instance;
    }
}
