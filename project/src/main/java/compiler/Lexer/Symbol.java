package compiler.Lexer;

public class Symbol {
    public enum Type_Symbol {
        IDENTIFIANT, MOT_CLEF,
        INT, FLOAT, BOOL, STRING,
        AND, OR, NOT,
        PARENTHESE_GAUCHE, PARENTHESE_DROITE, CROCHET_GAUCHE, CROCHET_DROIT, ACCOLADE_GAUCHE, ACCOLADE_DROITE,
        PLUS, MOINS, MULTIPLICATION, DIVISION, MODULO,
        EGAL, NOT_EGAL, INFERIEUR, SUPERIEUR, EGAL_OU_MOINS, EGAL_OU_PLUS,
        POINT, VIRGULE, POINT_VIRGULE,
        FIN_FICHIER
    }

    public Type_Symbol type;
    public String valeur;
    public int ligne;
    public int colonne;

    public Symbol(Type_Symbol type, String value, int ligne, int colonne) {
        this.type = type;
        this.valeur = value;
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public Type_Symbol getType() {
        return type;
    }

    public String getValeur() {
        return valeur;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public String toString() {
        return "TOKEN<" + type + ',' + valeur + ',' + ligne + ',' + colonne + ',' + '>';
    }
}