package parsing.json;

import org.junit.Test;
import parsing.model.ParseResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Purpose:
 */
public class JObjectTest {
    public static final Path TEST_FILE_DIR = Path.of("test", "parsing", "json", "files");

    @Test
    public void testParse() throws IOException {
        Path filePath = TEST_FILE_DIR.resolve("object.json");
        String objStr = Files.readString(filePath);

        JObject object = new JObject();
        ParseResult result = object.parse(objStr, 0);

        assertTrue(result.isValid());
        assertEquals(object.toString(), objStr);
    }
}
