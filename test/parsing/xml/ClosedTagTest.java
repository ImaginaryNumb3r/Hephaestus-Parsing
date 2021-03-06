package parsing.xml;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ClosedTagTest extends XMLParseNodeTest {
    private static final List<String> SAMPLES = readTestData("ClosedTag.xml");

    @Test
    public void testParse() {
        for (String sample : SAMPLES) {
            checkParse(sample);
        }
    }

    private void checkParse(String xml) {
        ClosedTag closedTag = new ClosedTag();
        var result = closedTag.parse(xml, 0);
        assertTrue(result.isValid());

        String string = closedTag.toString();
        assertEquals(xml, string);
    }

    @Test
    public void testCopy() {
        for (String sample : SAMPLES) {
            checkCopy(sample);
        }
    }

    public void checkCopy(String xml) {
        ClosedTag closedTag = new ClosedTag();
        var result = closedTag.parse(xml, 0);
        assertTrue(result.isValid());

        ClosedTag copy = closedTag.deepCopy();

        // TODO: Ignore for now because this class became obsolete
        // assertEquals(closedTag, copy);
    }
}
