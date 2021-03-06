package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
public class XMLTailTest extends XMLParseNodeTest {
    public static final List<String> TEST_DATA = readTestData("XMLTail.xml");

    @Test
    public void testParse() {
        for (String tail : TEST_DATA) {
            XMLTail token = new XMLTail();

            // TODO: < is not a valid character in Attribute texts
            checkParse(tail, tail, token, token::toString);
        }
    }
}
