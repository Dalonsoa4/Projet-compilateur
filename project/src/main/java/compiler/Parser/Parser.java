package compiler.Parser;

import compiler.Lexer.Symbol;
import compiler.Lexer.Lexer;

import java.io.Reader;
import java.util.ArrayList;

public class Parser {

    public ArrayList<Symbol> liste_token;

    public Parser(Reader input) {
        Lexer lexer = new Lexer(input);
        liste_token = lexer.getListe_token();
        System.out.println("Token à Parser : ");
        lexer.show_token();
    }
}
