package parsing.xml;

import parsing.model.basic.CopyNode;
import parsing.model.node.SequenceNode;
import parsing.model.terminal.StringTerminal;
import parsing.model.token.SpaceToken;
import parsing.model.token.WhitespaceToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Grammar: '<?xml' Attributes '?>'
 */
public final class XMLProlog extends SequenceNode implements CopyNode<XMLProlog> {
    private static final String START_TERMINAL = "<?xml";
    private static final String END_TERMINAL = "?>";
    private final SpaceToken _space;
    private final XMLAttributes _attributes;
    private final WhitespaceToken _trailingWhitespace;

    public XMLProlog() {
        super(new ArrayList<>());

        _space = new SpaceToken();
        _attributes = new XMLAttributes();
        _trailingWhitespace = new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
                new StringTerminal(START_TERMINAL),
                _space,
                _attributes,
                _trailingWhitespace,
                new StringTerminal(END_TERMINAL)
        ));
    }

    @Override
    public XMLProlog deepCopy() {
        XMLProlog copy = new XMLProlog();
        SpaceToken spaceCopy = _space.deepCopy();
        XMLAttributes attributesCopy = _attributes.deepCopy();
        WhitespaceToken whitespaceCopy = _trailingWhitespace.deepCopy();

        copy._space.setData(spaceCopy);
        copy._attributes.setData(attributesCopy);
        copy._trailingWhitespace.setData(whitespaceCopy);

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
        _space.reset();
        _attributes.reset();
        _trailingWhitespace.reset();
    }

    @Override
    public void setData(XMLProlog other) {
        reset();
        super.setData(other);
        _attributes.setData(other._attributes);
        _trailingWhitespace.setData(other._trailingWhitespace);

        if (!equals(other)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof XMLProlog)) return false;
        if (!super.equals(obj)) return false;
        XMLProlog other = (XMLProlog) obj;
        return Objects.equals(_attributes, other._attributes) &&
                Objects.equals(_space, other._space) &&
                Objects.equals(_trailingWhitespace, other._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _attributes, _trailingWhitespace);
    }

    @Override
    public String toString() {
        return START_TERMINAL + _space.toString()
            + _attributes.toString()
            + _trailingWhitespace.toString() + END_TERMINAL;
    }
}
