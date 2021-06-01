package parsing.json;

import parsing.model.CharTerminal;
import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * An object contains a list of members. A member is a key value pair.
 * Each Member is separated by commas.
 * Grammar: ( '{' Whitespace  '}' ) |
 *          ( '{' JAttributes '}' )
 */
public final class JObject extends SequenceNode implements CopyNode<JObject> {
    private final JAttributes _values;

    public JObject() {
        _values = new JAttributes();

        var tokens = Arrays.asList(
                new WhitespaceToken(), new CharTerminal('{'),
                new WhitespaceToken(), _values,
                new WhitespaceToken(), new CharTerminal('}')
        );
        _sequence.addAll(tokens);
    }

    private Optional<JAttribute> fetchAttribute(String name) {
        List<JAttribute> attributes = _values.getAttributes();
        return attributes.stream()
            .filter(attr -> attr.getKey().equals(name))
            .findFirst();
    }

    public Optional<JObject> fetchObject(String name) {
        return fetchAttribute(name).map(JAttribute::getObject);
    }

    public Optional<JArray> fetchArray(String name) {
        return fetchAttribute(name).map(JAttribute::getArray);
    }

    public Optional<JNumber> fetchNumber(String name) {
        return fetchAttribute(name).map(JAttribute::getNumber);
    }

    public Optional<String> fetchString(String name) {
        return fetchAttribute(name).map(JAttribute::getString);
    }

    public JObject getObject(String name) {
        return fetchObject(name).orElseThrow(() -> illegalArgumentException(name));
    }

    public JArray getArray(String name) {
        return fetchArray(name).orElseThrow(() -> illegalArgumentException(name));
    }

    public JNumber getNumber(String name) {
        return fetchNumber(name).orElseThrow(() -> illegalArgumentException(name));
    }

    public String getString(String name) {
        return fetchString(name).orElseThrow(() -> illegalArgumentException(name));
    }

    public boolean getBool(String name) {
        Optional<JAttribute> attribute = fetchAttribute(name);
        if (attribute.isEmpty()) {
            throw new IllegalArgumentException("Key \"" + name + "\" does not exist");
        }

        return attribute.get().getBoolean();
    }

    public boolean isNull(String name) {
        Optional<JAttribute> attribute = fetchAttribute(name);
        return attribute
            .orElseThrow(() -> illegalArgumentException(name))
            .isNull();
    }

    private static IllegalArgumentException illegalArgumentException(String name) {
        return new IllegalArgumentException("Key \"" + name + "\" does not exist");
    }

    @Override
    public void setData(JObject other) {
        super.setData(other);
    }

    @Override
    public JObject deepCopy() {
        JObject other = new JObject();
        other.setData(this);

        return other;
    }
}
