package compiler.Parser;

/*
* La classe représente une Statement
* Un Statement c'est une action que le programme execute
* toutes les lignes qui finissent par un ; sont des Statement
* les condifitions, if , ect sont des Statement
*/

public abstract class Statement extends ASTNode {}

class VarDeclStmt extends Statement {
    public final TypeNode type;
    public final String name;
    public final Expression initializer;

    public VarDeclStmt(TypeNode type, String name, Expression initializer) {
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toTree(String indent) {
        return indent + "VarDeclStmt\n"
                + indent + "  " + type.toTree("") + "\n"
                + indent + "  Identifier(" + name + ")\n"
                + initializer.toTree(indent + "  ");
    }
}