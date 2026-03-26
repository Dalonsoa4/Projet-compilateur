package compiler.Parser;

import compiler.Lexer.Symbol;
import compiler.Lexer.Lexer;

import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {

    private ArrayList<Symbol> liste_token;
    private int index_token;
    private ArrayList<Statement> liste_statements;

    public Parser(Reader input) {
        Lexer lexer = new Lexer(input);
        liste_token = lexer.getListe_token();
        index_token = 0;
        liste_statements = new ArrayList<Statement>();

        System.out.println("Token à Parser : ");
        lexer.show_token();
    }

    public ArrayList<Statement> getAST() {
        liste_statements.add(parseVarDecl());
        System.out.println(currentToken());
        if(currentToken().is_symbole_point_virgule()){
            advance();
            liste_statements.add(parseVarDecl());
        }
        return liste_statements;// doit devenir parseprogram()
    }


// _________________________________ Méthodes liées aux symboles _________________________________________________


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

    private boolean isKeyword(Symbol symbol) {
        if (symbol.getType() != Symbol.Type_Symbol.MOT_CLEF) {
            return false;
        }

        return true;
    }

// _________________________________ Méthodes liées au Parsing de déclarations de variables et de calculs _______________________________________________________


    private Statement parseVarDecl() {
        // faut gerer le cas où y'a plusieurs initialiseur à la suite
        TypeNode type = parseType();

        String name = match(Symbol.Type_Symbol.IDENTIFIANT).getValeur();

        match(Symbol.Type_Symbol.EGAL_ASSIGNATION);

        Expression initializer = parseExpression();

        return new VarDeclStmt(type, name, initializer);
    }

    private TypeNode parseType() {
        Symbol current = currentToken();

        if (current == null) {throw new RuntimeException("Type attendu, mais fin de fichier");}

        if (isKeyword(current)) {advance();return new TypeNode(current.getValeur());}
        throw new RuntimeException("Erreur de syntaxe ligne " + current.getLigne()+ ", colonne " + current.getColonne()+ " : Mot clef attendu mais on a trouvé "+ current.getType() + " (" + current.getValeur() + ")");
    }

    private Expression parsePrimary() {
        Symbol current = currentToken();

        if (current == null) {throw new RuntimeException("Expression attendue, mais fin de fichier");}

        if (current.getType() == Symbol.Type_Symbol.INT) {
            int value = Integer.parseInt(current.getValeur()); // pour choper la valeur du symbole, toString() fonctionne pas
            advance();
            return new IntLiteral(value);
        }

        if (current.getType() == Symbol.Type_Symbol.FLOAT) {
            double value = Double.parseDouble(current.getValeur()); // pour choper la valeur du symbole, toString() fonctionne pas
            advance();
            return new FloatLiteral(value);
        }

        // gere la priorité des opérations quand y'a des parentheses
        if (current.getType() == Symbol.Type_Symbol.PARENTHESE_GAUCHE) {
            advance();

            Expression expr = parseExpression();

            if (currentToken() == null || currentToken().getType() != Symbol.Type_Symbol.PARENTHESE_DROITE) {
                throw new RuntimeException("Erreur de syntaxe ligne " + current.getLigne()+ ", colonne " + current.getColonne()+ " : parenthèse droite attendue");
            }

            advance();
            return expr;
        }

        throw new RuntimeException("Erreur de syntaxe ligne " + current.getLigne()+ ", colonne " + current.getColonne()+ " : entier attendu mais on a trouvé "+ current.getType() + " (" + current.getValeur() + ")");
    }

    private Expression parseExpression() {
        Expression left = parseMultiplicative(); // permet de gerer la priorité des opérations

        while (currentToken() != null && currentToken().is_symbol_for_calcul_low_priority()) {

            String op = currentToken().getValeur();
            advance();

            Expression right = parseMultiplicative();
            left = new BinaryExpr(op, left, right);
        }

        return left;
    }

    private Expression parseMultiplicative() {
        Expression left = parsePrimary();

        while (currentToken() != null && currentToken().is_symbol_for_calcul_high_priority()) {

            String op = currentToken().getValeur();
            advance();

            Expression right = parsePrimary();
            left = new BinaryExpr(op, left, right);
        }

        return left;
    }
}