package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class XMLTagTest extends XMLParseNodeTest {
    public static final List<String> TEST_DATA;

    static {
        TEST_DATA = readTestData("tags.xml");
    }

    @Test
    public void testParse() {
        for (String data : TEST_DATA) {
            XMLTag token = new XMLTag();

            checkParse(data, data, token, token::toString);
        }
    }
}
