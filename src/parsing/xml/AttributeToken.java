package parsing.xml;

import parsing.model.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: Text Whitespace '=' Whitespace "Text"
 */
public final class AttributeToken extends SequenceNode implements CopyNode<AttributeToken> {
    private final ElementNameToken _name;
    private final ContentNode _value;

    public AttributeToken() {
        this(new ElementNameToken(), new ContentNode("\""));
    }

    public AttributeToken(ElementNameToken name, ContentNode value) {
        super(Arrays.asList(
            name, new WhitespaceToken(),
            new CharTerminal('='), new WhitespaceToken(),
            value
        ));
        _name = name;
        _value = value;
    }

    public String getName() {
        return _name.toString();
    }

    public String getValue() {
        return _value.getContent();
    }

    public void setName(String name) {
        _name.setName(name);
    }

    public void setValue(String value) {
        _value.setContent(value);
    }

    @Override
    public AttributeToken deepCopy() {
        AttributeToken copy = new AttributeToken();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(AttributeToken other) {
        var sequenceCopy = other._sequence.stream()
                .map(ParseNode::deepCopy)
                .collect(Collectors.toList());

        _sequence.clear();
        _sequence.addAll(sequenceCopy);
        _name.setData(other._name);
        _value.setData(other._value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeToken)) return false;
        AttributeToken that = (AttributeToken) o;
        return  Objects.equals(_name, that._name)
                && Objects.equals(_value, that._value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _value);
    }
}
