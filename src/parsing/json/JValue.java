package parsing.json;

import essentials.contract.NoImplementationException;
import parsing.model.basic.AbstractParseNode;
import parsing.model.basic.CopyNode;
import parsing.model.terminal.StringTerminal;
import parsing.model.token.ContentToken;
import parsing.model.util.ParseNode;
import parsing.model.util.ParseResult;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: JObject | JArray | ( '"' "Text '"' ) | JNumber | JBool | null
 */
public class JValue extends AbstractParseNode implements CopyNode<JValue> {
    private JValueType _type;
    private CopyNode<?> _value;

    public JValue() {
    }

    /**
     * @implNote This tries to parse all of the different types and remembers the according type as field.
     * When retrieving the value later, the object must be cast to its correct type.
     */
    @Override
    protected ParseResult parseImpl(String chars, int index) {
        var map = new HashMap<CopyNode<?>, JValueType>();
        map.put(new JObject(), JValueType.OBJECT);
        map.put(new JArray(), JValueType.ARRAY);
        map.put(new ContentToken("\""), JValueType.STRING);
        map.put(new JNumber(), JValueType.NUMBER);
        map.put(new JBool(), JValueType.BOOL);
        map.put(new StringTerminal("null"), JValueType.NULL);

        for (var entry : map.entrySet()) {
            CopyNode<?> node = entry.getKey();
            ParseResult result = tryParse(node, chars, index);

            if (result.isValid()) {
                _value = node;
                _type = entry.getValue();
                return result;
            }
        }

        return ParseResult.invalid(index, "Cannot parse Json value");
    }

    public JValueType getType() {
        return _type;
    }

    public boolean isBoolean() {
        return _type == JValueType.BOOL;
    }

    public boolean isObject() {
        return _type == JValueType.OBJECT;
    }

    public boolean isArray() {
        return _type == JValueType.ARRAY;
    }

    public boolean isString() {
        return _type == JValueType.STRING;
    }

    public boolean isNumber() {
        return _type == JValueType.NUMBER;
    }

    public boolean isNull() {
        return _type == JValueType.NULL;
    }

    public Optional<Boolean> fetchBoolean() {
        return isBoolean() ? ((JBool) _value).fetchBool() : Optional.empty();
    }

    public boolean getBoolean() {
        if (!isBoolean()) {
            String type = _type != null ? _type.toString() : "none";
            throw new IllegalStateException("Cannot return boolean from JValue. Current type is: \" " + type +" \"");
        }

        //noinspection ConstantConditions
        return ((JBool) _value).getBool();
    }

    public JObject getObject() {
        return isObject() ? ((JObject) _value) : null;
    }

    public Optional<JArray> fetchArray() {
        return isArray() ? Optional.of((JArray) _value) : Optional.empty();
    }

    public JArray getArray() {
        return fetchArray().orElse(null);
    }

    public ParseResult tryParse(ParseNode node, String chars, int index) {
        return node.parse(chars, index);
    }

    @Override
    public String toString() {
        return _value == null ? "unparsed" : _value.toString();
    }

    @Override
    public String asString() {
        return _value == null ? null : _value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JValue)) return false;
        JValue other = (JValue) obj;

        return _type == other._type
            && _value == other._value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_type, _value);
    }

    @Override
    public JValue deepCopy() {
        JValue other = new JValue();
        other.setData(this);

        return other;
    }

    @Override
    public void setData(JValue other) {
        throw new NoImplementationException();
    }

    @Override
    public void reset() {
        _type = null;
        _value.reset();
    }
}
