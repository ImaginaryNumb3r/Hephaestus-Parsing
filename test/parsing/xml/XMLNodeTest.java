package parsing.xml;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
public class XMLNodeTest extends XMLParseNodeTest {

    @Test
    public void testParseComments() {
        for (TestPair testPair : CommentTokenTest.TEST_DATA) {
            XMLNode token = new XMLNode();

            checkParse(testPair.data, testPair.data, token, token::toString);
            assertTrue(token.isComment());

            assertTrue(token.getCommentContent().isPresent());
            assertEquals(testPair.expected, token.getCommentContent().get());
        }
    }

    @Test
    public void testParseTags() {
        for (String tag : XMLTagTest.TEST_DATA) {
            XMLNode token = new XMLNode();

            checkParse(tag, tag, token, token::toString);
            assertTrue(token.isTag());

            assertTrue(token.getTag().isPresent());
            assertEquals(tag, token.getTag().get().toString());
        }
    }
}
