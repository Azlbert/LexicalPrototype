package Lexical;

import Grammar.Errors.Manager;
import Grammar.Errors.Error;
import Grammar.Tokens.Classification;
import Grammar.Tokens.Instance;
import Grammar.Tokens.Token;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;


public class Analyzer {
    private final Automata automata;
    private String word;
    
    public Analyzer(){
        automata = Automata.getInstance();
        word = "";
        
        String _alphabetic = "_abcdefghijklmnopqrstuvwxyz";
        _alphabetic += _alphabetic.toUpperCase();
        String numeric = "0123456789";
        
        Automata identifierNode = new Automata(Token.IDENTIFIER);
        Automata numericNode = new Automata(Token.NUMERIC);
        Automata numericErrorNode = new Automata(Error.X003);
        
        saveToAutomata(Classification.KEYWORD, Token.IDENTIFIER,
                new SimpleEntry(Token.NULL_CHARACTER,identifierNode));
        
        saveToAutomata(Classification.KEYWORD, Token.IDENTIFIER,
                new SimpleEntry(Token.NULL_CHARACTER,identifierNode));
        
        saveToAutomata(Classification.ARITHMETIC);
        saveToAutomata(Classification.COMPARISON);
        saveToAutomata(Classification.LOGIC);
        saveToAutomata(Classification.ASSIGNMENT);
        saveToAutomata(Classification.SEPARATOR);
        
        createConnection(_alphabetic+numeric, identifierNode, identifierNode);
        createConnection(_alphabetic, automata, identifierNode);
        createConnection(numeric, numericNode, numericNode);
        createConnection(_alphabetic, numericNode, numericErrorNode);
        createConnection(_alphabetic+numeric, numericErrorNode, numericErrorNode);
        createConnection(numeric, automata, numericNode);
    }
    
    public final void saveToAutomata(Classification classification){
        saveToAutomata(classification, Token.NONE);
        
        
    }
    
    /**
     *
     * @param classification
     * @param innerType
     * @param exits
     */
    public final void saveToAutomata(Classification classification, Token innerType,
            Entry<Character,Automata> ... exits){
        classification.getTokens().forEach((token) -> {
            Automata pointer = Automata.getInstance();
            int lng = token.getLexem().length();
            for (int i = 0;i < lng; i++){
                pointer = pointer.addCharacter(token.getLexem().charAt(i));
                if(i < lng-1 && innerType != Token.NONE){
                    pointer.setType(innerType);
                }
                for(Entry<Character,Automata> entry : exits){
                    pointer.createConnection(entry.getKey(),entry.getValue());
                }
            }
            pointer.setType(token);
        });
    }
    
    /**
     * Create multiple connections betwen the parent -> child nodes, using the characters of a String as connectors
     * @param characters all the characters connections
     * @param parent the parent
     * @param child the child
     */
    public final void createConnection(String characters, Automata parent, Automata child){
        for (int i = 0;i < characters.length(); i++){
            parent.createConnection(characters.charAt(i),child);
        }
    }
    
    /**
     *
     * @param fileName
     * @throws IOException
     */
    public void analyzeScript(String fileName) throws IOException{
        FileReader inputStream = null;
        Grammar.Tokens.Token savedToken = Token.NONE;
        Grammar.Errors.Error savedError = Error.NONE;
        int i;
        Character c;
        boolean letMeEnd = true;
        try {
            inputStream = new FileReader(fileName);
            System.out.println("\n");
            Automata pointer = Automata.getInstance();
            while ((i = inputStream.read()) != -1 || letMeEnd) {
                if(i == -1){
                    i = ' ';
                    letMeEnd = false;
                }
                c = Character.toChars(i)[0];
                pointer = pointer.getNextNode(c);
                if(!word.isEmpty() && pointer == automata){
                    Instance instance = new Instance(savedToken, word);
                    if(savedError != Error.NONE){
                        Manager.getInstance().reportError(savedError, instance);
                    }
                    Grammar.Tokens.List.getInstance().addInstance(instance);
                    word = "";
                    pointer = pointer.getNextNode(c);
                } else if (pointer == automata && word.isEmpty()){
                    Instance instance = new Instance(savedToken, word);
                    Grammar.Tokens.List.getInstance().addInstance(instance);
                    Grammar.Errors.Manager.getInstance().reportError(Grammar.Errors.Error.X001, instance);
                }
                
                if (pointer != automata){
                    word += c;
                }
                
                
                savedToken = pointer.getType();
                savedError = pointer.getReportedError();
                //System.out.println(c +" -> " + pointer + " type: " + savedType);
            }
        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'\n" + ex);
        } catch(IOException ex) {
            System.out.println( "Error reading file '" + fileName + "'\n" + ex);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
