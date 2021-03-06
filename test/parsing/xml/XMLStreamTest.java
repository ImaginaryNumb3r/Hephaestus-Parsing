package parsing.xml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class XMLStreamTest extends XMLParseNodeTest {

    /**
     * TODO: Re-test this once I've implemented TagStreamImpl::filter
     */
    public void testXMLStream() throws IOException {
        Path testXml = TEST_FILE_DIR.resolve("document.xml");
        XMLDocument xmlDocument = XMLDocument.ofFile(testXml);

        TagStream drawTags = xmlDocument.stream()
            .filter("AssetDeclaration")
            .filter("GameObject")
            .filter("Draws");

        // Find attributes of first tag in the list of tags.
        Optional<String> attrValue = drawTags
            .filter("ScriptedModelDraw")
            .findFirst()
            .getAttribute("id");

        assertTrue(attrValue.isPresent());

    }
}
