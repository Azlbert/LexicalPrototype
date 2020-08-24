/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grammar.Symbols;

import java.util.ArrayList;

/**
 *
 * @author Alberto
 */
public class SymbolTable {
    private ArrayList<Symbol> table;
    
    public boolean addSymbol(String symbol){
        for(Symbol record : table){
            if (symbol.equals(record.getValue())) {
                return false;
            }
        }
        table.add(new Symbol(symbol));
        return true;
    }
}
