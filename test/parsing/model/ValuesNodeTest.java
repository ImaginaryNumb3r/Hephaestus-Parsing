package parsing.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ValuesNodeTest extends ParseNodeTest {
    private static final List<String> TEST_DATA;

    static {
        TEST_DATA = Arrays.asList("\"first\" \"second\"   \"third\" \"forth\"\"fifth\"", "\"one\"");
    }

    @Test
    public void testNonEmpty() {
        for (String testString : TEST_DATA) {
            var token = new ValuesNode<>(
                () -> new ContentToken("\""),
                WhitespaceToken::new
            );

            checkParse(testString, testString, testString.length(), token, token::toString, token::toString);
        }
    }

    @Test
    public void testEmpty() {
        String testString = " ";

        var token = new ValuesNode<>(
            () -> new ContentToken("\""),
            WhitespaceToken::new
        );

        String trimmed = testString.trim();
        checkParse(trimmed, testString, trimmed.length(), token, token::toString, () -> " ");
    }
}
