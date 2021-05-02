package parsing.xml;

import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Grammar: '<?xml' Attributes '?>'
 * TODO: Major Issue - the elements in sequence nodes must always be in sync with the fields.
 */
public final class XMLProlog extends SequenceNode implements CopyNode<XMLProlog> {
    private static final String START_TERMINAL = "<?xml";
    private static final String END_TERMINAL = "?>";
    // private final SpaceToken _space;
    private final XMLAttributes _attributes;
    private final WhitespaceToken _trailingWhitespace;

    public XMLProlog() {
        super(new ArrayList<>());

        // _space = new SpaceToken();
        _attributes = new XMLAttributes();
        _trailingWhitespace = new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
                new StringTerminal(START_TERMINAL),
                // _space,
                _attributes,
                _trailingWhitespace,
                new StringTerminal(END_TERMINAL)
        ));
    }

    @Override
    public XMLProlog deepCopy() {
        XMLProlog copy = new XMLProlog();
        // SpaceToken spaceCopy = _space.deepCopy();
        XMLAttributes attributesCopy = _attributes.deepCopy();
        WhitespaceToken whitespaceCopy = _trailingWhitespace.deepCopy();

        // copy._space.setData(spaceCopy);
        copy._attributes.setData(attributesCopy);
        copy._trailingWhitespace.setData(whitespaceCopy);

        return copy;
    }

    @Override
    public void setData(XMLProlog other) {
        super.setData(other);
        // _space.setData(other._space);
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
            // Objects.equals(_space, other._space) &&
                Objects.equals(_trailingWhitespace, other._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _attributes, _trailingWhitespace);
    }

    @Override
    public String toString() {
        return START_TERMINAL /*+ _space.toString() */
            + _attributes.toString()
            + _trailingWhitespace.toString() + END_TERMINAL;
    }
}
