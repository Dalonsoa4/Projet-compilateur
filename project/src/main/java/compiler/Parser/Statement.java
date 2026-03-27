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
    public final boolean is_declaration_final;

    public VarDeclStmt(Boolean is_final, TypeNode type, String name, Expression initializer) {
        this.is_declaration_final = is_final;
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toTree(String indent) {
        return indent + "VarDeclStmt" + (is_declaration_final ? "(final)" : "") + "\n"
                + indent + "  " + type.toTree("") + "\n"
                + indent + "  Identifier(" + name + ")\n"
                + indent + "  AssignmentOperator(=)\n"
                + initializer.toTree(indent + "  ");
    }
}