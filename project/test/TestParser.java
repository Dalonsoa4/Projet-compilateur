import static org.junit.Assert.assertNotNull;

import compiler.Parser.Parser;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;

public class TestParser {

    @Test
    public void test() {
        String input = "INT x = 1 + 2";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        lexer.show_token();
        Parser parser = new Parser(lexer.getListe_token());
        assertNotNull(lexer.getNextSymbol());
    }
}