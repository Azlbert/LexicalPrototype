/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Errors;

/**
 *
 * @author Alberto
 */
public enum Error {
    NONE,
    
    X001(CompilerSection.Lexical, "El caracter no coincide con la grammatica"),
    X002(CompilerSection.Lexical, "Los caracteres no coinciden con la gramatica"),
    X003(CompilerSection.Lexical, "Error en la variable, no se puede iniciar con un digito");
    
    private CompilerSection section;
    private String msg;
    
    private Error(){}
    private Error(CompilerSection section, String msg){
        this.section = section;
        this.msg = msg;
    }

    public CompilerSection getSection() {
        return section;
    }

    public String getMsg() {
        return msg;
    }
}
