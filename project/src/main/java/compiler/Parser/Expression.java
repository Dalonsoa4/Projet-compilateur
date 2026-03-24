package compiler.Parser;

/*
 * La classe sert à représenter une Expression
 * Une Expression c'est tout ce qui produit une valeur dans le langage par exemple :
 * 1, x, x+2, square(5), ect..
 * Il y a plusieurs type d'expression d'où l'interet de faire une classe abstraite
*/


abstract class Expression extends ASTNode {}

class IntLiteral extends Expression {
    public final int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public String toTree(String indent) {
        return indent + "IntLiteral(" + value + ")";
    }
}

class BinaryExpr extends Expression {
    public final String operator;
    public final Expression left;
    public final Expression right;

    public BinaryExpr(String operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toTree(String indent) {
        return indent + "ExprBinaire"+"\n"
                + left.toTree(indent + "  ") + "\n"
                + indent + "  " + "OperatorCalcul " + operator + "\n"
                + right.toTree(indent + "  ");
    }
}