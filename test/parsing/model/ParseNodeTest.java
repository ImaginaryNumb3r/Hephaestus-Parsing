package parsing.model;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrick Plieschnegger
 */
public class ParseNodeTest {

    protected void checkParse(String expected, ParseNode token) {
        checkParse(expected, expected, expected.length(), token, token::toString, token::toString);
    }

    protected void checkParse(String expected, String data, ParseNode token, Supplier<String> dataSupplier) {
        checkParse(expected, data, data.length(), token, dataSupplier, token::toString);
    }

    protected void checkParse(String expected, String data, int expectedParseLength, ParseNode token,
                              Supplier<String> dataSupplier, Supplier<String> toString
    ) {
        var result = token.parse(data, 0);

        String message = "Asserting that the token could be parsed fails for: \"" + data + "\"";
        assertTrue(message, result.isValid());

        message = "Asserting that the consume index is correct fails for: " + data;
        assertEquals(message, expectedParseLength, result.index());

        message = "Comparison between consume output and expected output fails";
        assertEquals(message, expected, dataSupplier.get());

        message = "Raw comparison between consume input and output fails";
        assertEquals(message, data, toString.get());

        message = "Asserting that a copy is equal failed for: " + token.getClass().getName();
        ParseNode copy = token.deepCopy();
        assertEquals(message, token, copy);

        message = "Repeated parsing creates non-identical copies for: " + token.getClass().getName();
        copy.parse(data, 0);
        assertEquals(message, token, copy);
    }
}
