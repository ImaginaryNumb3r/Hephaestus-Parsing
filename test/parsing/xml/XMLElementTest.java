package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class XMLElementTest extends XMLParseNodeTest {
    public static final List<String> TEST_DATA;

    static {
        TEST_DATA = readTestData("tags.xml");
    }

    @Test
    public void testParse() {
        for (String data : TEST_DATA) {
            XMLElement token = new XMLElement();

            checkParse(data, data, token, token::toString);
        }
    }
}
