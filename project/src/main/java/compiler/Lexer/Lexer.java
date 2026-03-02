package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Lexer {
    public Reader input;
    public int char_actuel;
    public int ligne;
    public int colonne;
    public ArrayList<Symbol> liste_token;

    public Lexer(Reader input) {
        this.input = input;
        this.ligne = 0;
        this.colonne = 0;
        liste_token = new ArrayList<>();
        char_suivant();
        stock_token();
    }

    public void show_token() {
        for (Symbol token : liste_token) {
            System.out.println(token);
        }
    }

    public void char_suivant() {
        try {
            char_actuel = input.read();
            if (char_actuel == '\n') {
                ligne++;
                colonne = 0;} else {
                colonne++;}
        } catch (IOException e) {
            char_actuel = -1; // probleme a l'input
        }
    }

    public int check_char_suivant() {
        // permet de regarder le caractere suivant, read() permet pas de revenir en arriere
        int char_suivant;
        try {
            input.mark(1);
            char_suivant = input.read();
            input.reset();
        } catch (IOException e) {
            char_suivant = -1;
        }
        return char_suivant;
    }

    public Symbol lexing_nombre() {
        int colonne_depart = colonne;
        StringBuilder nombre = new StringBuilder();
        boolean is_float = false;

        // pour les cas de .234
        if (char_actuel == '.') {
            is_float = true;
            nombre.append('0');
            nombre.append('.');
            char_suivant();
        }

        while (Character.isDigit(char_actuel) || char_actuel == '.') {
            if (char_actuel == '.') {
                if (is_float) {
                    throw new RuntimeException("format de nombre invalide");
                }
                is_float = true;
            }
            nombre.append((char) char_actuel);
            char_suivant();
        }

        // pour les cas de 00234 -> devient 234
        String valeur = nombre.toString();
        if (!is_float) {
            while (valeur.length() > 1 && valeur.charAt(0) == '0') {
                valeur = valeur.substring(1);
            }
        }

        return new Symbol(is_float ? Symbol.Type_Symbol.FLOAT : Symbol.Type_Symbol.INT, valeur, ligne, colonne_depart);
    }

    public Symbol lexing_identifiant_et_motclef() {
        int colonne_depart = colonne;
        StringBuilder identifiant = new StringBuilder();

        while (Character.isLetterOrDigit(char_actuel) || char_actuel == '_') {
            identifiant.append((char) char_actuel);
            char_suivant();
        }

        String valeur = identifiant.toString();
        switch (valeur) { // liste des mots clefs
            case "int":
            case "float":
            case "bool":
            case "string":
            case "true":
            case "false":
            case "if":
            case "else":
            case "while":
            case "for":
            case "fun":
            case "return":
            case "final":
            case "rec":
            case "array":
            case "free":
                return new Symbol(Symbol.Type_Symbol.MOT_CLEF, valeur, ligne, colonne_depart);
            default:
                return new Symbol(Symbol.Type_Symbol.IDENTIFIANT, valeur, ligne, colonne_depart);
        }
    }

    public Symbol lexing_String() {
        int colonne_depart = colonne;
        StringBuilder string = new StringBuilder();
        char_suivant(); // passe le guillemet ouvrant

        while (char_actuel != '"' && char_actuel != -1) { // jusqu'a la fin du fichier ou guillemets fermant
            if (char_actuel == '\\') {
                char_suivant();
                if (char_actuel == '"' || char_actuel == '\\') { // pour ce genre de cas \"Hello\"
                    string.append((char) char_actuel);
                } else {
                    throw new RuntimeException("Suite de char invalide");
                }
            } else {
                string.append((char) char_actuel);
            }
            char_suivant();
        }

        if (char_actuel == '"') {
            char_suivant(); // passe le guillemet fermant
        } else {
            throw new RuntimeException("La fin du string est pas determinee");
        }

        return new Symbol(Symbol.Type_Symbol.STRING, string.toString(), ligne, colonne_depart);
    }

    public void stock_token(){
        Symbol symbole = getNextSymbol();
        while (symbole.getType() != Symbol.Type_Symbol.FIN_FICHIER) {
            liste_token.add(symbole);
            symbole = getNextSymbol();
        }
    }

    public Symbol getNextSymbol() {
        while (char_actuel != -1) {
            if (Character.isWhitespace(char_actuel)) { // on ignore les espaces blancs
                char_suivant();
                continue;
            }

            if (char_actuel == '$') { // quand c'est un commentaires on skip la ligne
                while (char_actuel != '\n' && char_actuel != -1) {
                    char_suivant();
                }
                continue;
            }

            if (Character.isDigit(char_actuel)) { // si il faut reconnaitre un nombre
                return lexing_nombre();
            }

            if (Character.isLetter(char_actuel)) { // si c'est une lettre sans rien alors c'est un mot clef ou un id
                return lexing_identifiant_et_motclef();
            }

            if (char_actuel == '"') { // si il faut reconnaitre un string
                return lexing_String();
            }

            int colonne_depart = colonne; // evite de gerer manuellement les incrementations
            switch (char_actuel) { // check chaque symbole special
                case '+':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.PLUS, "+", ligne, colonne_depart);
                case '-':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.MOINS, "-", ligne, colonne_depart);
                case '*':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.MULTIPLICATION, "*", ligne, colonne_depart);
                case '/':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.DIVISION, "/", ligne, colonne_depart);
                case '%':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.MODULO, "%", ligne, colonne_depart);
                case '(':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.PARENTHESE_GAUCHE, "(", ligne, colonne_depart);
                case ')':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.PARENTHESE_DROITE, ")", ligne, colonne_depart);
                case '[':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.CROCHET_GAUCHE, "[", ligne, colonne_depart);
                case ']':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.CROCHET_DROIT, "]", ligne, colonne_depart);
                case '{':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.ACCOLADE_GAUCHE, "{", ligne, colonne_depart);
                case '}':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.ACCOLADE_DROITE, "}", ligne, colonne_depart);
                case '.':
                    if (Character.isDigit(check_char_suivant())) {
                        return lexing_nombre();
                    }
                    else {
                        char_suivant();return new Symbol(Symbol.Type_Symbol.POINT, ".", ligne, colonne_depart);
                    }
                case ',':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.VIRGULE, ",", ligne, colonne_depart);
                case ';':
                    char_suivant();
                    return new Symbol(Symbol.Type_Symbol.POINT_VIRGULE, ";", ligne, colonne_depart);
                case '=':
                    char_suivant();
                    if (char_actuel == '=') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.EGAL, "==", ligne, colonne_depart);
                    }
                    return new Symbol(Symbol.Type_Symbol.EGAL, "=", ligne, colonne_depart);
                case '!':
                    char_suivant();
                    if (char_actuel == '=') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.NOT_EGAL, "!=", ligne, colonne_depart);
                    }
                    return new Symbol(Symbol.Type_Symbol.NOT, "!", ligne, colonne_depart);
                case '<':
                    char_suivant();
                    if (char_actuel == '=') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.EGAL_OU_MOINS, "<=", ligne, colonne_depart);
                    }
                    return new Symbol(Symbol.Type_Symbol.INFERIEUR, "<", ligne, colonne_depart);
                case '>':
                    char_suivant();
                    if (char_actuel == '=') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.EGAL_OU_PLUS, ">=", ligne, colonne_depart);
                    }
                    return new Symbol(Symbol.Type_Symbol.SUPERIEUR, ">", ligne, colonne_depart);
                case '&':
                    char_suivant();
                    if (char_actuel == '&') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.AND, "&&", ligne, colonne_depart);
                    }
                    throw new RuntimeException("Mauvais char qui suit & :" + (char) char_actuel);
                case '|':
                    char_suivant();
                    if (char_actuel == '|') {
                        char_suivant();
                        return new Symbol(Symbol.Type_Symbol.OR, "||", ligne, colonne_depart);
                    }
                    throw new RuntimeException("Mauvais char qui suit | : " + (char) char_actuel);
                default:
                    throw new RuntimeException("Char non reconnu" + (char) char_actuel);
            }
        }
        return new Symbol(Symbol.Type_Symbol.FIN_FICHIER, "EOF", ligne, colonne);
    }
}