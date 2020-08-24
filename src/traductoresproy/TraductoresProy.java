package traductoresproy;

import java.io.IOException;

public class TraductoresProy {


    public static void main(String[] args) throws IOException {
        Lexical.Analyzer lexicalAnalyzer = new Lexical.Analyzer();
        lexicalAnalyzer.analyzeScript("txt.txt");
        System.out.println("\nAnalyzed: ");
        //Syntax.Analyzer.SytaxAnalyzer();
        Grammar.Tokens.List.getInstance().printList();
        Grammar.Errors.Manager.getInstance().printStatus();
    }
}
