package parsing.model.node;

import org.junit.Assert;
import org.junit.Test;
import parsing.model.terminal.CharTerminal;
import parsing.model.token.TextToken;
import parsing.model.util.ParseResult;

public class SeparatedNodesTest {
    private final String testNoValue = "";
    private final String test1Value = "first";
    private final String test2Values = "first,second";
    private final String test3Values = "first,second,third";

    @Test
    public void mustNotParseValues() {
        var nodes = new SeparatedNodes<>(TextToken::new, () -> new CharTerminal(','));
        ParseResult parsed = nodes.parse(testNoValue, 0);
        Assert.assertTrue(parsed.isInvalid());
        Assert.assertEquals(0, nodes._elements.size());
        Assert.assertEquals(testNoValue, nodes.toString());
    }

    @Test
    public void mustParseValues() {
        var nodes = new SeparatedNodes<>(TextToken::new, () -> new CharTerminal(','));
        ParseResult parsed = nodes.parse(test1Value, 0);
        Assert.assertTrue(parsed.isValid());
        Assert.assertEquals(1, nodes._elements.size());
        Assert.assertEquals(test1Value, nodes.toString());

        parsed = nodes.parse(test2Values, 0);
        Assert.assertTrue(parsed.isValid());
        Assert.assertEquals(2, nodes._elements.size());
        Assert.assertEquals(3, nodes.getAllSequentially().size());

        parsed = nodes.parse(test3Values, 0);
        Assert.assertTrue(parsed.isValid());
        Assert.assertEquals(3, nodes._elements.size());
        Assert.assertEquals(5, nodes.getAllSequentially().size());
    }
}