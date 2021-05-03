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
    private final XMLAttributes _attributes;
    private final WhitespaceToken _trailingWhitespace;

    public XMLProlog(XMLAttributes attributes, WhitespaceToken trailingWhitespace) {
        super(Arrays.asList(
            new StringTerminal(START_TERMINAL),
            attributes,
            trailingWhitespace,
            new StringTerminal(END_TERMINAL)
        ));

        _attributes = attributes;
        _trailingWhitespace = trailingWhitespace;
    }

    public XMLProlog() {
        this(new XMLAttributes(), new WhitespaceToken());
    }

    @Override
    public XMLProlog deepCopy() {
        return new XMLProlog(_attributes.deepCopy(), _trailingWhitespace.deepCopy());
    }

    @Override
    public void setData(XMLProlog other) {
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
                Objects.equals(_trailingWhitespace, other._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _attributes, _trailingWhitespace);
    }

    @Override
    public String toString() {
        return START_TERMINAL + _attributes.toString() + _trailingWhitespace.toString() + END_TERMINAL;
    }
}
