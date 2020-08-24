package Lexical;

import Grammar.Tokens.Token;
import Grammar.Errors.Error;
import java.util.HashMap;
import java.util.Map;


public class Automata {
    private static Automata instance = null;
    
    private Map<Character, Automata> map = new HashMap<>();
    private Grammar.Tokens.Token type = Grammar.Tokens.Token.NONE;
    private Error reportedError = Error.NONE;
    
    public static Automata getInstance(){
        if(instance == null){
            instance = new Automata();
        }
        return instance;
    }
    
    protected Automata(){}
    
    public Automata(Token type){
        this.type = type;
    }
    
    public Automata(Error reportedError){
        this.reportedError = reportedError;
    }

    @Override
    public String toString() {
        return "Automata{" + type + '}';
    }
    
    public Automata addCharacter(Character character){
        Automata a = map.get(character);
        if(a == null){
            a = new Automata();
            map.put(character, a);
        }
        return a;
    }
    
    public Automata addCharacter(Character character, Error reportedError){
        Automata a = map.get(character);
        if(a == null){
            a = new Automata();
            map.put(character, a);
        }
        return a;
    }
    
    public Automata createConnection(Character character, Automata child){
        Automata a = map.get(character);
        if(a == null){
            map.put(character, child);
        }
        return a == null ? child : this;
    }
    
    public Automata getNextNode(Character character){
        Automata a = map.get(character);
        if(a == null){
            a = map.get(Token.NULL_CHARACTER);
            if(a != null){
                a = a.getNextNode(character);
            }
        }
        return a == null ? instance : a;
    }

    public Token getType() {
        return type;
    }

    public void setType(Token type) {
        if(this.type == Token.NONE){
            this.type = type;
        }
    }

    public Error getReportedError() {
        return reportedError;
    }
    
    
}