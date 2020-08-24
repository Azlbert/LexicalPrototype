package Syntax;

import Grammar.Tokens.Classification;
import java.util.ArrayList;

public class Analyzer {
    private static Block rootCodeBlock = new Block();
    //public static Lexical.TokenLexems lex = new Lexical.TokenLexems();
    
    public static void AddToken(String word, Grammar.Tokens.Classification c){
    //    Token token = new Token(word,c,lex.getType(word));
    //    rootCodeBlock.addToken(token);
    }
    
    public static void PrintTokens(){
        System.out.println("Here are the tokens: ");
        System.out.println(rootCodeBlock.tokensToString());
    }
    
    public static void SytaxAnalyzer(){
        if(rootCodeBlock.AnalizeBlock()){
            System.out.println("ALL OK");
        } else {
            System.out.println("NOT OK");
        }
    }
}
@SuppressWarnings("LeakingThisInConstructor")
class Block {
    private ArrayList<Block> accesibleBlocks;
    private boolean addNewStatement;
    private ArrayList<Statement> statements = new ArrayList<>();
    
    public Block(){
        accesibleBlocks = new ArrayList<>();
        accesibleBlocks.add(this);
    }
    
    public Block(ArrayList<Block> parentBlock){
        parentBlock.add(this);
    }
    
    public void addToken(Token addThis){
        Statement statement;
        if(statements.isEmpty() || addNewStatement){
            statement = new Statement();
            statements.add(statement);
            addNewStatement = false;
        } else {
            statement = statements.get(statements.size()-1);
        }
        statement.addToken(addThis);
        if((addThis.getType() == Grammar.Tokens.Token.END_STATEMENT || addThis.getType() == Grammar.Tokens.Token.CURLY_BRACKETS_CLOSE)
                && !(Statement.openedBrackets || Statement.openedParentheses)){
            addNewStatement = true;
        }
    }
    
    public boolean AnalizeBlock(){
        boolean status = true;
        for(Statement statement : statements){
            if(!statement.AnalyzeTokens()){
                status = false;
            }
            System.out.println(statement.tokensToString());
        }
        return status;
    }
    
    public String tokensToString(){
        StringBuilder strStatement = new StringBuilder();
        
        for(Statement statement : statements){
            strStatement.append(statement.tokensToString());
        }
        return strStatement.toString();
    }
}

class Statement {
    // An statement could be a Token, block of code or another statement
    private ArrayList<Object> statements = new ArrayList<>();
    public static boolean openedBrackets = false;
    public static boolean openedParentheses = false;

    public ArrayList<Object> getStatements() {
        return statements;
    }
    
    public void addToken(Token thisToken){
        // Si el ultimo elemento de la lista es instancia de X agregar
        // statement
        // block
        //System.out.println("Testing token " + thisToken.getClassification() + " value " + thisToken.getValue());
        Object lastElement = statements.size() > 0 ? statements.get(statements.size()-1) : null;
        //System.out.println("Last element: " + lastElement);
        //System.out.println("");
        if(lastElement != null && lastElement instanceof Block && thisToken.getType() != Grammar.Tokens.Token.CURLY_BRACKETS_CLOSE){
            //System.out.println("Pass to Block");
            ((Block) lastElement).addToken(thisToken);
        } else if (lastElement != null && lastElement instanceof Statement && thisToken.getType() != Grammar.Tokens.Token.PARENTHESES_CLOSE){
            //System.out.println("Pass to Statement");
            ((Statement) lastElement).addToken(thisToken);
            //System.out.println(thisToken);
        } else {
            if(openedBrackets && thisToken.getType() == Grammar.Tokens.Token.CURLY_BRACKETS_CLOSE) {
                openedBrackets = false;
                //System.out.println("Closing Block");
            } else if (openedParentheses && thisToken.getType() == Grammar.Tokens.Token.PARENTHESES_CLOSE) {
                openedParentheses = false;
                //System.out.println("Closing Statement");
            }
            
            statements.add(thisToken);
            
            if(!openedBrackets && thisToken.getType() == Grammar.Tokens.Token.CURLY_BRACKETS_OPEN){
                //System.out.println(" --> Addign BLOCK");
                addBlock(new Block());
                openedBrackets = true;
            } else if (!openedParentheses && thisToken.getType() == Grammar.Tokens.Token.PARENTHESES_OPEN){
                //System.out.println(" --> Addign STATEMENT");
                addStatement(new Statement());
                openedParentheses = true;
            }
        }
        

    }
    
    public void addBlock(Block thisBlock){
        statements.add(thisBlock);
    }
    
    public void addStatement(Statement thisStatement){
        statements.add(thisStatement);
    }
    
    public boolean AnalyzeTokens(){
        if(statements == null || statements.isEmpty()){
            return false;
        }
        int size = statements.size();
        Token token;
        Grammar.Tokens.Token type;
        Classification classification;
        
        if(size >= 1 && statements.get(0) instanceof Token){
            token = (Token)statements.get(0);
            type = token.getType();
            classification = token.getClassification();
            if(type == Grammar.Tokens.Token.INT || type == Grammar.Tokens.Token.FLOAT || type == Grammar.Tokens.Token.BOOLEAN || type == Grammar.Tokens.Token.CHARACTER){
                if(size >= 2 && statements.get(1) instanceof Token){
                    token = (Token)statements.get(1);
                    classification = token.getClassification();
                    if(classification == Classification.IDENTIFIER){
                        if(size >= 3 && statements.get(2) instanceof Token){
                            token = (Token)statements.get(2);
                            type = token.getType();
                            if(type == Grammar.Tokens.Token.END_STATEMENT && size == 3){
                                return true;
                            } else if (type == Grammar.Tokens.Token.ASSIGN){
                                if(size >= 4 && statements.get(3) instanceof Token){
                                    token = (Token)statements.get(3);
                                    classification = token.getClassification();
                                    if(classification == Classification.IDENTIFIER || classification == classification.NUMERIC){
                                        if(size >= 5 && statements.get(4) instanceof Token){
                                            token = (Token)statements.get(4);
                                            classification = token.getClassification();
                                            type = token.getType();
                                            if(type == Grammar.Tokens.Token.END_STATEMENT && size == 5){
                                                return true;
                                            } else if(classification == Classification.ARITHMETIC){
                                                if(size >= 6 && statements.get(5) instanceof Token){
                                                    token = (Token)statements.get(5);
                                                    classification = token.getClassification();
                                                    type = token.getType();
                                                    if(classification == Classification.IDENTIFIER || classification == classification.NUMERIC){
                                                        if(size >= 7 && statements.get(6) instanceof Token){
                                                            token = (Token)statements.get(6);
                                                            type = token.getType();
                                                            if(type == Grammar.Tokens.Token.END_STATEMENT && size == 7){
                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if(classification == Classification.IDENTIFIER){
                if(size >= 2 && statements.get(1) instanceof Token){
                    token = (Token)statements.get(1);
                    type = token.getType();
                    if (type == Grammar.Tokens.Token.ASSIGN){
                        if(size >= 3 && statements.get(2) instanceof Token){
                            token = (Token)statements.get(2);
                            classification = token.getClassification();
                            if(classification == Classification.IDENTIFIER || classification == classification.NUMERIC){
                                if(size >= 4 && statements.get(3) instanceof Token){
                                    token = (Token)statements.get(3);
                                    classification = token.getClassification();
                                    type = token.getType();
                                    if(type == Grammar.Tokens.Token.END_STATEMENT && size == 4){
                                        return true;
                                    } else if(classification == Classification.ARITHMETIC){
                                        if(size >= 5 && statements.get(4) instanceof Token){
                                            token = (Token)statements.get(4);
                                            classification = token.getClassification();
                                            type = token.getType();
                                            if(classification == Classification.IDENTIFIER || classification == classification.NUMERIC){
                                                if(size >= 6 && statements.get(5) instanceof Token){
                                                    token = (Token)statements.get(5);
                                                    type = token.getType();
                                                    if(type == Grammar.Tokens.Token.END_STATEMENT && size == 6){
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if(type == Grammar.Tokens.Token.IF || type == Grammar.Tokens.Token.WHILE){
                if(size >= 2 && statements.get(1) instanceof Token){
                    token = (Token)statements.get(1);
                    type = token.getType();
                    if(type == Grammar.Tokens.Token.PARENTHESES_OPEN){
                        if(size >= 3 && statements.get(2) instanceof Statement && testExpression((Statement)statements.get(2))){
                            if(size >= 4 && statements.get(3) instanceof Token){
                                token = (Token)statements.get(3);
                                type = token.getType();
                                if(type == Grammar.Tokens.Token.PARENTHESES_CLOSE){
                                    if(size >= 5 && statements.get(4) instanceof Token){
                                        token = (Token)statements.get(4);
                                        type = token.getType();
                                        if(type == Grammar.Tokens.Token.CURLY_BRACKETS_OPEN){
                                            if(size >= 6 && statements.get(5) instanceof Block && ((Block)statements.get(5)).AnalizeBlock()){
                                                if(size >= 7 && statements.get(6) instanceof Token){
                                                    token = (Token)statements.get(6);
                                                    type = token.getType();
                                                    if(type == Grammar.Tokens.Token.CURLY_BRACKETS_CLOSE){
                                                        if(size == 7){
                                                            return true;
                                                        } else if (size >= 8 && statements.get(7) instanceof Token){
                                                            token = (Token)statements.get(7);
                                                            type = token.getType();
                                                            if(type == Grammar.Tokens.Token.ELSE){
                                                                if (size >= 9 && statements.get(8) instanceof Token){
                                                                    token = (Token)statements.get(8);
                                                                    type = token.getType();
                                                                    if(type == Grammar.Tokens.Token.CURLY_BRACKETS_OPEN){
                                                                        if(size >= 10 && statements.get(9) instanceof Block && ((Block)statements.get(9)).AnalizeBlock()){
                                                                            if(size >= 11 && statements.get(10) instanceof Token){
                                                                                token = (Token)statements.get(10);
                                                                                type = token.getType();
                                                                                if(type == Grammar.Tokens.Token.CURLY_BRACKETS_CLOSE && size == 11){
                                                                                    return true;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean testExpression(Statement s){
        if(s == null){
            return false;
        }
        ArrayList<Object> stm = s.getStatements();
        if(stm.isEmpty()){
            return false;
        }
        int size = stm.size();
        Token token;
        Classification classification;
        
        if(size >= 1 && stm.get(0) instanceof Token){
            token = (Token)stm.get(0);
            classification = token.getClassification();
            if(classification == Classification.NUMERIC || classification == Classification.IDENTIFIER){
                if(size == 1){
                    return true;
                } else if (size >= 2 && stm.get(1) instanceof Token){
                    token = (Token)stm.get(1);
                    classification = token.getClassification();
                    if(classification == Classification.COMPARISON){
                        if (size >= 3 && stm.get(2) instanceof Token){
                            token = (Token)stm.get(2);
                            classification = token.getClassification();
                            if(classification == Classification.NUMERIC || classification == Classification.IDENTIFIER){
                                if(size == 3){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public String tokensToString(){
        StringBuilder strStatement = new StringBuilder();
        
        for(Object statement : statements){
            if(statement instanceof Token){
                strStatement.append((Token)statement).append(" ");
            } else if(statement instanceof Block){
                strStatement.append("<< BLOCK >> ");
                /*strStatement.append("\n <<----   New Block   ---->> \n").append(((Block)statement).tokensToString());
                strStatement.append("\n <<----   End Block   ---->>");*/
            } else if(statement instanceof Statement){
                strStatement.append("<< STATEMENT >> ");
                /*strStatement.append("\n <<---- New Statement ---->> \n").append(((Statement)statement).tokensToString());
                strStatement.append("\n <<---- End Statement ---->> \n");*/
            }
        }
        return strStatement.toString();
    }
}

class Token {
    private Grammar.Tokens.Classification classification;
    
    private Grammar.Tokens.Token type;
    private Object value;
    
    private int numberLine;
    private int startPosition;
    private int endPosition;
    
    public Token(String value, Grammar.Tokens.Classification c, Grammar.Tokens.Token t){
        this.setClassification(c);
        this.setValue(value);
        this.setType(t);
    }

    @Override
    public String toString() {
        String a = "{ " + classification + " , '" + value + "' }";
        return a;
    }
    
    private String valueToString(){
        return ""+(type == Grammar.Tokens.Token.IDENTIFIER ? value : type);
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Grammar.Tokens.Token getType() {
        return type;
    }

    public void setType(Grammar.Tokens.Token type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}