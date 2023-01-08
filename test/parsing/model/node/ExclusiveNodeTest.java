package parsing.model.node;

import org.junit.Test;
import parsing.model.terminal.StringTerminal;
import parsing.model.token.IntegerToken;
import parsing.model.token.WhitespaceToken;
import parsing.model.util.ParseResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExclusiveNodeTest {

    @Test
    public void mustParse() {
        var nodes = List.of(new StringTerminal("stringValue"), new IntegerToken(), new WhitespaceToken());
        var exclusiveNode = new ExclusiveNode<>(nodes, List.of("terminal", "integer", "whitespace"));
        ParseResult result = exclusiveNode.parse("stringValue", 0);

        assertTrue(result.isValid());
        assertEquals("stringValue", exclusiveNode.toString());
        assertEquals("terminal", exclusiveNode.getType());
        assertEquals(StringTerminal.class, exclusiveNode.getNodeClass());

        exclusiveNode.reset();
        result = exclusiveNode.parse("1234567890", 0);

        assertTrue(result.isValid());
        assertEquals("1234567890", exclusiveNode.toString());
        assertEquals("integer", exclusiveNode.getType());
        assertEquals(IntegerToken.class, exclusiveNode.getNodeClass());

        exclusiveNode.reset();
        result = exclusiveNode.parse(" \t\r\n", 0);

        assertTrue(result.isValid());
        assertEquals(" \t\r\n", exclusiveNode.toString());
        assertEquals("whitespace", exclusiveNode.getType());
        assertEquals(WhitespaceToken.class, exclusiveNode.getNodeClass());
    }
}