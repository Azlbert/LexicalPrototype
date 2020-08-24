package Grammar.Tokens;

@SuppressWarnings("LeakingThisInConstructor")
public enum Token{
    NONE,
    IDENTIFIER(Classification.IDENTIFIER),
    NUMERIC(Classification.NUMERIC),
    
    // KEYWORD
    INT (Classification.KEYWORD,"entero"),
    FLOAT (Classification.KEYWORD,"decimal"),
    BOOLEAN (Classification.KEYWORD,"boleano"),
    CHARACTER (Classification.KEYWORD,"caracter"),
    VOID (Classification.KEYWORD,"vacio"),
    IF (Classification.KEYWORD,"si"),
    ELSE (Classification.KEYWORD,"o"),
    WHILE (Classification.KEYWORD,"mientras"),
    RETURN (Classification.KEYWORD,"regresa"),
    TRUE (Classification.KEYWORD,"verdadero"),
    FALSE (Classification.KEYWORD,"fal"),
    
    // ASSIGNMENT
    ASSIGN (Classification.ASSIGNMENT,"="),
    
    // SPECIAL ASSIGNMENT
    ASSIGN_PLUS,
    ASSIGN_MINUS,
    
    // SEPARATOR
    SPACE (Classification.SEPARATOR,Character.toString((char)32)),
    LINE_FEED (Classification.SEPARATOR,Character.toString((char)10)),
    CARRIAGE_RETURN (Classification.SEPARATOR,Character.toString((char)13)),
    TAB (Classification.SEPARATOR,Character.toString((char)9)),
    END_STATEMENT (Classification.SEPARATOR,";"),
    PARENTHESES_OPEN (Classification.SEPARATOR,"("),       // (
    PARENTHESES_CLOSE (Classification.SEPARATOR,")"),      // )
    CURLY_BRACKETS_OPEN (Classification.SEPARATOR,"{"),    // {
    CURLY_BRACKETS_CLOSE (Classification.SEPARATOR,"}"),   // }
    
    // ARITHMETIC
    PLUS (Classification.ARITHMETIC,"+"),
    MINUS (Classification.ARITHMETIC,"-"),
    MULTIPLICATION (Classification.ARITHMETIC,"*"),
    DIVISION (Classification.ARITHMETIC,"/"),
    
    // COMPARISON
    EQUAL_THAN (Classification.COMPARISON,"=="),
    NOT_EQUAL_THAN (Classification.COMPARISON,"!="),
    LESS_THAN (Classification.COMPARISON,"<"),
    LESS_EQUAL_THAN (Classification.COMPARISON,"<="),
    GREATER_THAN (Classification.COMPARISON,">"),
    GREATER_EQUEAL_THAN (Classification.COMPARISON,">="),
    
    // LOGIC
    AND (Classification.LOGIC,"&&"),
    OR (Classification.LOGIC,"||"),
    NOT (Classification.LOGIC,"!");
    
    private Classification classification;
    private String lexem;
    public final static char NULL_CHARACTER = '\u0000';
    
    private Token(Classification classification, String lexem){
        this(classification);
        this.lexem = lexem;
    }
    
    private Token(Classification classification){
        this.classification = classification;
        if(classification != Classification.NONE){
            classification.addToken(this);
        }
    }
    
    private Token(){
        this.classification = Classification.NONE;
        this.lexem = "";
    }

    public Classification getClassification() {
        return classification;
    }

    public String getLexem() {
        return lexem;
    }
    
    
}
