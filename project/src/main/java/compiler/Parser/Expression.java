package compiler.Parser;

/*
 * La classe sert à représenter une Expression
 * Une Expression c'est tout ce qui produit une valeur dans le langage par exemple :
 * 1, x, x+2, square(5), ect..
 * Il y a plusieurs type d'expression d'où l'interet de faire une classe abstraite
*/


abstract class Expression extends ASTNode {}