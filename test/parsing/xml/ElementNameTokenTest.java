package parsing.xml;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ElementNameTokenTest extends XMLParseNodeTest {
    private static final List<String> TEST_DATA = Arrays.asList("Text1Number_Underscore ");

    @Test
    public void testParse() {
        for (String data : TEST_DATA) {
            var token = new ElementNameToken();

            checkParse(data.trim(), data, data.length() - 1, token, token::toString, () -> token.toString() + " ");
        }
    }
}
