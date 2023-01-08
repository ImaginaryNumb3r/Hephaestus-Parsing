package parsing.json;

import org.junit.Test;
import parsing.model.util.ParseResult;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 09.11.2019
 * Purpose:
 */
public class JAttributeTest {

    @Test
    public void testParse() {
        var attributes = Arrays.asList("\"useJSP\": false", "\"useDataStore\": true");

        for (String attributeStr : attributes) {
            JAttribute token = new JAttribute();
            ParseResult result = token.parse(attributeStr, 0);

            assertTrue(result.isValid());
            assertEquals(attributeStr, token.toString());
        }
    }
}
