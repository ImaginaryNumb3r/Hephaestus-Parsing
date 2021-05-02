package parsing.xml;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class XMLAttributesTest extends XMLParseNodeTest {
    @Test
    public void testParse() {
        for (String attributeStr : readTestData("XMLAttributes.xml")) {
            XMLAttributes token = new XMLAttributes();
            token.parse(attributeStr, 0);

            assertEquals(attributeStr, token.toString() + ">");

            String expected = attributeStr.substring(0, attributeStr.length() - 1);
            checkParse(expected, attributeStr, attributeStr.length() - 1,  token, token::toString, () -> token.toString() + ">");
        }
    }
}
