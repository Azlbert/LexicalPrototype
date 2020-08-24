package Grammar.Tokens;

import java.util.ArrayList;
import java.util.List;

public enum Classification{
    NONE,
    NUMERIC,
    IDENTIFIER,
    KEYWORD,
    ASSIGNMENT,
    SPECIAL_ASSIGNMENT,
    SEPARATOR,
    ARITHMETIC,
    COMPARISON,
    LOGIC;
    
    List<Token> tokens;
    private Classification(){
        tokens = new ArrayList<>();
    }
    
    public void addToken(Token token){
        if(token != Token.NONE && !tokens.contains(token)){
            tokens.add(token);
        }
    }
    
    public List<Token> getTokens(){
        return tokens;
    }
}