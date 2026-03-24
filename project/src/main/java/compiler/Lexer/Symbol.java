package compiler.Lexer;

public class Symbol {
    public enum Type_Symbol {
        IDENTIFIANT, MOT_CLEF, COLLECTION,
        INT, FLOAT, BOOL, STRING,
        BOOL_LITERAL,
        AND, OR,
        EGAL_ASSIGNATION, EGAL_COMPARAISON,
        PARENTHESE_GAUCHE, PARENTHESE_DROITE, CROCHET_GAUCHE, CROCHET_DROIT, ACCOLADE_GAUCHE, ACCOLADE_DROITE,
        PLUS, MOINS, MULTIPLICATION, DIVISION, MODULO,
        NOT_EGAL, INFERIEUR, SUPERIEUR, EGAL_OU_MOINS, EGAL_OU_PLUS,
        POINT, VIRGULE, POINT_VIRGULE,RANGE,
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

    public Boolean is_symbol_for_calcul_low_priority() {
        switch (type){
            case PLUS:
            case MOINS:
                return true;
            default:
                return false;
        }
    }

    public Boolean is_symbol_for_calcul_high_priority() {
        switch (type){
            case MULTIPLICATION:
            case DIVISION:
            case MODULO:
                return true;
            default:
                return false;
        }
    }

    public Boolean is_symbol_for_operation(){
        switch (type){
            case OR:
            case AND:
                return true;
            default:
                return false;
        }
    }

    public Boolean is_symbol_for_comparaison(){
        switch (type){
            case EGAL_COMPARAISON:
            case NOT_EGAL:
            case EGAL_OU_PLUS:
            case EGAL_OU_MOINS:
            case INFERIEUR:
            case SUPERIEUR:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "TOKEN<" + type + ',' + valeur + ',' + ligne + ',' + colonne + ',' + '>';
    }
}