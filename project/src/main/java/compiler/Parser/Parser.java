package compiler.Parser;

import compiler.Lexer.Symbol;
import compiler.Lexer.Lexer;

import java.io.Reader;
import java.util.ArrayList;

public class Parser {

    private ArrayList<Symbol> liste_token;
    private int index_token;

    public Parser(Reader input) {
        Lexer lexer = new Lexer(input);
        liste_token = lexer.getListe_token();
        index_token = 0;

        System.out.println("Token à Parser : ");
        lexer.show_token();
    }

    public Statement getAST() {
        return parseVarDecl(); // doit devenir parseprogram()
    }

    private Symbol currentToken() {
        if (index_token >= liste_token.size()) {
            return null;
        }
        return liste_token.get(index_token);
    }

    private void advance() {
        index_token++;
    }

    private Symbol match(Symbol.Type_Symbol expectedType) {
        // vérif que le symbole est bien celui attendu
        Symbol current = currentToken();

        if (current == null) {
            throw new RuntimeException("Fin de fichier inattendue");
        }

        if (current.getType() != expectedType) {
            throw new RuntimeException(
                    "Erreur de syntaxe ligne " + current.getLigne()
                            + ", colonne " + current.getColonne()
                            + " : attendu " + expectedType
                            + " mais trouvé " + current.getType()
                            + " (" + current.getValeur() + ")"
            );
        }

        advance();
        return current;
    }

    private boolean isKeyword(String keyword) {
        Symbol current = currentToken();

        if (current.getType() != Symbol.Type_Symbol.MOT_CLEF) {
            return false;
        }

        return current.getValeur().equals(keyword);
    }

    private TypeNode parseType() {
        Symbol current = currentToken();

        if (current == null) {
            throw new RuntimeException("Type attendu, mais fin de fichier");
        }

        if (isKeyword("INT")) {
            advance();
            return new TypeNode("INT");
        }

        // faudra faire tous les types ici

        throw new RuntimeException(
                "Erreur de syntaxe ligne " + current.getLigne()
                        + ", colonne " + current.getColonne()
                        + " : type attendu, trouvé "
                        + current.getType() + " (" + current.getValeur() + ")"
        );
    }

    private Expression parsePrimary() {
        Symbol current = currentToken();

        if (current == null) {
            throw new RuntimeException("Expression attendue, mais fin de fichier");
        }

        if (current.getType() == Symbol.Type_Symbol.INT) {
            int value = Integer.parseInt(current.getValeur()); // pour choper la valeur du symbole toString() fonctionne pas
            advance();
            return new IntLiteral(value);
        }

        throw new RuntimeException(
                "Erreur de syntaxe ligne " + current.getLigne()
                        + ", colonne " + current.getColonne()
                        + " : entier attendu, trouvé "
                        + current.getType() + " (" + current.getValeur() + ")"
        );
    }

    private Expression parseExpression() {
        Expression left = parsePrimary();

        while (currentToken() != null // Faudra gerer les autres que addition
                && currentToken().getType() == Symbol.Type_Symbol.PLUS) {

            String op = currentToken().getValeur();
            advance();

            Expression right = parsePrimary();
            left = new BinaryExpr(op, left, right);
        }

        return left;
    }

    private Statement parseVarDecl() {
        // faut gerer le cas où y'a plusieurs initialiseur à la suite
        TypeNode type = parseType();

        String name = match(Symbol.Type_Symbol.IDENTIFIANT).getValeur();

        match(Symbol.Type_Symbol.EGAL_ASSIGNATION);

        Expression initializer = parseExpression();

        return new VarDeclStmt(type, name, initializer);
    }
}