package compiler.Parser;

abstract class ASTNode {
    public abstract String toTree(String indent);

    @Override
    public String toString() {
        return toTree("");
    }
}

class TypeNode extends ASTNode { // classe qui représente les types de variable (int, string, ect)
    public final String name; // nom du type

    public TypeNode(String name) {
        this.name = name;
    }

    @Override
    public String toTree(String indent) {
        return indent + "Type(" + name + ")";
    }
}