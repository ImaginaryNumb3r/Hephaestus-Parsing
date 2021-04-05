package parsing.model;

import org.junit.Test;
import parsing.xml.XMLParseNodeTest;

import static org.junit.Assert.*;

public class SpaceTokenTest extends XMLParseNodeTest {

    @Test
    public void testSetSpace() {
        try {
            SpaceToken spaceToken = SpaceToken.of("abc");
            fail("Space Tokens cannot have non-whitespace characters as content");
        } catch (IllegalArgumentException ex) {
            // Assertion succeeded.
        }

        String whitespaces = " \t\n\r";
        SpaceToken spaceToken1 = SpaceToken.of(whitespaces);
        assertEquals(whitespaces, spaceToken1.asString());

        SpaceToken spaceToken2 = new SpaceToken();
        spaceToken2.setSpace(whitespaces);
        assertEquals(whitespaces, spaceToken2.asString());

        assertEquals("Equality between space tokens failed", spaceToken1, spaceToken2);

        // Assert that there are no trailing leftovers from a previous state.
        spaceToken1.setSpace("\t");
        assertEquals("Space Token has wrong length after setSpace", 1, spaceToken1.length());
        assertEquals("Space Token has wrong content after setSpace", "\t", spaceToken1.asString());
    }

    @Test
    public void testMinimumLength() {
        SpaceToken spaceToken = new SpaceToken();
        assertEquals("Space Tokens after construction must have length 1", 1, spaceToken.length());
        assertEquals("Space Tokens must have a single space after construction", " ", spaceToken.asString());

        spaceToken.reset();
        assertEquals("Space Tokens after reset must have length 1", 1, spaceToken.length());
        assertEquals("Space Tokens must have a single space after reset", " ", spaceToken.asString());

        try {
            spaceToken.setSpace("");
            fail("Space Tokens cannot have empty strings as content");
        } catch (IllegalArgumentException ex) {
            // Assertion succeeded.
        }
    }
}
