package parsing.json;

import parsing.model.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: Whitespace "Key" Whitespace ":" JValue
 */
public final class JAttribute extends SequenceNode implements CopyNode<JAttribute> {
    private ContentToken _key;
    private JValue _value;

    public JAttribute() {
        _key = new ContentToken("\"");
        _value = new JValue();

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), _key,
                        new WhitespaceToken(), new CharTerminal(':'),
                        new WhitespaceToken(), _value
                )
        );
    }

    public String getKey() {
        return _key.getContent();
    }

    public JValue getValue() {
        return _value;
    }

    public JValueType getType() {
        return _value.getType();
    }

    public boolean isBoolean() {
        return _value.isBoolean();
    }

    public boolean isObject() {
        return _value.isObject();
    }

    public boolean isArray() {
        return _value.isArray();
    }

    public boolean isString() {
        return _value.isString();
    }

    public boolean isNumber() {
        return _value.isNumber();
    }

    public boolean isNull() {
        return _value.isNull();
    }

    public Optional<Boolean> fetchBoolean() {return _value.fetchBoolean();}

    public boolean getBoolean() {return _value.getBoolean();}

    public JObject getObject() {return _value.getObject();}

    public JArray getArray() {return _value.getArray();}

    public String getString() {
        return _value.getString();
    }

    public JNumber getNumber() {
        return _value.getNumber();
    }

    @Override
    public void setData(JAttribute other) {
        super.setData(other);
    }

    @Override
    public JAttribute deepCopy() {
        JAttribute other = new JAttribute();
        other.setData(this);

        return other;
    }
}
