import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;

public class TestLexer {
    
    @Test
    public void test() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        lexer.show_token();
        assertNotNull(lexer.getNextSymbol());
    }

}
/*    @Test
    public void test2() {
        String input = "# Source is a single file. No imports, etc. \n" +
                "# Comments look like this. No block comments. \n" +
                "# The language is strongly and statically typed. \n" +
                "# Base types are:\n" +
                "#    INT (signed 32-bit), \n" +
                "#    FLOAT (32-bit),  \n" +
                "#    BOOL (true/false),  \n" +
                "#    STRING. \n" +
                "# INT values are automatically promoted to FLOAT values in mixed expressions. \n" +
                "# There is no dedicated char type. Characters are represented by INTegers. \n" +
                "# Strings are immutable. \n" +
                "  \n" +
                "# Operations: \n" +
                "#  For INT and FLOAT, available operations are \n" +
                "#      +, -, * , /, - (unary). \n" +
                "#      ==, =/=, <, >, <=, >= \n" +
                "#  For INT: \n" +
                "#      % \n" +
                "#  For BOOL: \n" +
                "#      && (and operator), || (or operator), ==, =/= \n" +
                "#  For STRING \n" +
                "#      + (concatenation), ==, =/= \n" +
                "#  The i-th characters/element in a STRING/ARRAY can be read by the index operator [i]. \n" +
                "  \n" +
                "# Built-in functions: \n" +
                "#      not(BOOL)            : negates a boolean value, returns a boolean value \n" +
                "#      str(INT)           : turns the character (an INT value) into a STRING, returns a STRING \n" +
                "#      length(STRING or ARRAY)  : gives the length of a STRING or ARRAY, returns an INTeger \n" +
                "#      floor(FLOAT)    : returns the largest INT less than or equal the FLOAT value \n" +
                "#      ceil(FLOAT)    : returns the smallest INT greather than or equal the FLOAT value \n" +
                "# Exceptions: \n" +
                "# Run-time errors terminate the running program. \n" +
                "# Can happen when: \n" +
                "#    Out of memory \n" +
                "#    Division by zero \n" +
                "#    Out-of-bounds ARRAY and STRING access \n" +
                "#    FLOAT->INT overflow error \n" +
                "  \n" +
                "# Operator precedence: \n" +
                "#     function and constructor calls \n" +
                "#     parenthesis \n" +
                "#     collection field access operator . \n" +
                "#     index operator \n" +
                "#     *,/,% \n" +
                "#     +,-, unary - \n" +
                "#     ==, =/=, <, >, <=, >= \n" +
                "#     &&, || \n" +
                "# Operators with same precedence are left-associative. \n" +
                "# (We won’t try to nestle inequalities in our test, such as “a < b < c”.) \n" +
                " \n" +
                "# Constants must be declared at the top of the source file (if not respected, throw a semantic error). \n" +
                "# Constant declarations can use expressions and other constants that \n" +
                "# have been declared earlier. Only base types can be used for constants. \n" +
                "  \n" +
                "final INT i = 3; \n" +
                "final FLOAT j = 3.2*5.0; \n" +
                "final INT k = i*3; \n" +
                "final STRING message = \"Hello\"; \n" +
                "final BOOL isEmpty  = true; \n" +
                "  \n" +
                "# Constant declarations are followed by collection definitions. \n" +
                "coll Point { \n" +
                "    INT x; \n" +
                "    INT y; \n" +
                "} \n" +
                "coll Person { \n" +
                "    STRING name; \n" +
                "    Point location; \n" +
                "    INT[] history; \n" +
                "} \n" +
                "  \n" +
                "# Global variables are initialised in the order in which they appear. \n" +
                "# Variables do not always have an initialiser. \n" +
                "# Initialiser can be an expression. \n" +
                "INT a = 3; \n" +
                "# For ARRAYs, only one-dimensional ARRAYs are required (higher order ARRAY programming can be implemented as an extra feature). \n" +
                "# To initialise ARRAY or collection variables, they must be assigned to an existing collection or ARRAY, or a new ARRAY or collection must be created. \n" +
                "INT[] c  = INT ARRAY [5] ;  # new ARRAY of length 5 \n" +
                "Person d = Person(\"me\", Point(3,7), INT ARRAY [i*2] );  # new collection \n" +
                "  \n" +
                "# Functions: \n" +
                "# Functions have parameters and a return type. The return type \n" +
                "# can be a type or void (in which case nothing is indicated). \n" +
                "# Return can take expressions and declarations. \n" +
                "# Base type arguments are always passed by value. \n" +
                "# Collections and ARRAYs are always passed by reference. \n" +
                "# There are built-in functions for I/O: \n" +
                "#  read_INT, read_FLOAT, read_STRING, print_INT, print_FLOAT, print, println \n" +
                "# all \"read\" functions take nothing as argument, as they read for the standard input, and return the type specified. All “print” functions print on the standard output (they can be seen as print functions). \n" +
                "# \"print_INT\" and \"print_FLOAT\" take an \"INT\" and \"FLOAT\" as argument respectively, and return nothing or True/False if it succeeded or not (to be decided at the code generation phase; for now you can make an arbitrary choice). \n" +
                "# \"print\" and \"println\" can take anything as argument (including any primitive type or no argument). \"println\" will add an end-of-line at the end of what is written. The output is the same as the other \"print\" functions \n" +
                "# HINT: print_FLOAT should accept “-a” as an argument, where “a” is a FLOAT variable.  \n" +
                "# Function calls can forward-reference functions, even in initialisers of global variables, but not in constants. \n" +
                "  \n" +
                "# Local variables: \n" +
                "# Functions and while/if/else/for blocks can declare local variables and values \n" +
                "# mixed with statements. \n" +
                "# Their initialisation follows the same rules as for global variables. \n" +
                "  \n" +
                "# Scope: \n" +
                "# Lexical scoping. \n" +
                "# Local variables can shadow variables with the same name in surrounding scopes. \n" +
                "# Keywords, types, functions, constants, and variables share one name space. \n" +
                "# All names are case sensitive. \n" +
                "  \n" +
                "# Control structures: \n" +
                "# for, while, if, if/else \n" +
                "# Control structures bodies are always block statements. \n" +
                " \n" +
                "# for loops are initialised with 3 arguments: \n" +
                "#\t- an initialised variable. \n" +
                "#\t- a range, taking the form of “<initial value> -> <max value>”. \n" +
                "#\t- the updated value of the variable for the next iteration \n" +
                "# The loop begins by assigning the variable with the initial value of the range and stops when it reaches the max value (so <max value> is not included in the range). \n" +
                "  \n" +
                "# The left side of an assignment must be either: \n" +
                "#    a variable \n" +
                "#    an element of an ARRAY \n" +
                "#         a[3] = 1234;   # a is an ARRAY of INT \n" +
                "#    a field access to a collection such as: \n" +
                "#         a.x = 123; \n" +
                "#         a[3].x = 12; \n" +
                "#    To simplify the compiler, the left side cannot be an expression, this is not allowed: \n" +
                "#         someFunctionThatReturnsAnArray()[2] = 2; \n" +
                "#    Assigning an ARRAY or collection copies the reference. \n" +
                "                                          \n" +
                "def INT square(INT v) { \n" +
                "    return v*v; \n" +
                "} \n" +
                "  \n" +
                "def Point copyPoints(Point[] p) { \n" +
                "    return Point(p[0].x+p[1].x, p[0].y+p[1].y); \n" +
                "} \n" +
                "                             \n" +
                "def main() { \n" +
                "    INT value = read_INT();\n" +
                "    println(square(value));\n" +
                "    INT i;\n" +
                "    for (i; 1 -> 100; i+1) { \n" +
                "        while (value=/=3) { \n" +
                "            if (i > 10){ \n" +
                "                # .... \n" +
                "            } else { \n" +
                "                # .... \n" +
                "            } \n" +
                "        } \n" +
                "    } \n" +
                "\n" +
                "    i = (i+2)*2; \n" +
                "} \n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        lexer.show_token();
        assertNotNull(lexer.getNextSymbol());
    }
*/