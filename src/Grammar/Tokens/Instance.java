/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Tokens;

/**
 *
 * @author Alberto
 */
public class Instance {
    private Grammar.Tokens.Token token;
    private String value;
    
    public Instance(Grammar.Tokens.Token token, String value){
        this.token = token;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Instance{" + "token= " + token + ", value= " + value + '}';
    }
    
    
}
