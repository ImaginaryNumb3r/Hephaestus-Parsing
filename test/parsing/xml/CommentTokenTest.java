package parsing.xml;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class CommentTokenTest extends XMLParseNodeTest {
    public static final List<TestPair> TEST_DATA = Arrays.asList(
            new TestPair("<!-- comment -->", " comment "),
            new TestPair("<!---->", ""),
            new TestPair("<!--a-->", "a")
    );

    @Test
    public void testParse() {
        for (TestPair testPair : TEST_DATA) {
            String expected = testPair.expected;
            String comment = testPair.data;
            CommentToken token = new CommentToken();

            checkParse(expected, comment, token, token::getContent);
        }
    }
}
