package parsing.xml;

import parsing.model.basic.AbstractParseNode;
import parsing.model.basic.CopyNode;
import parsing.model.terminal.CharTerminal;
import parsing.model.token.SpaceToken;
import parsing.model.token.TextToken;
import parsing.model.token.WhitespaceToken;
import parsing.model.util.NodeTuple;
import parsing.model.util.ParseResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * TODO: We can turn the "XMLAttributes" token into a one-or-more token.
 * Grammar: '<' Name (Space Attributes)? Whitespace
 */
public final class TagHeader extends AbstractParseNode implements CopyNode<TagHeader> {
    private final CharTerminal _terminal;
    private final TextToken _name;
    // TODO: We only need that space, in case there are attributes. Otherwise, this is not necessary.
    private final SpaceToken _space;
    private final XMLAttributes _attributes;
    private final WhitespaceToken _whitespace;
    private boolean _hasAttributes;

    public TagHeader() {
        _terminal = new CharTerminal('<');
        _name = new TextToken();
        _space = new SpaceToken();
        _attributes = new XMLAttributes();
        _whitespace= new WhitespaceToken();

        _hasAttributes = false;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        // Parse mandatory '<'.
        ParseResult result = _terminal.parse(chars, index);
        if (result.isInvalid()) {
            return result;
        }
        int nextIndex = result.index();

        // Parse mandatory tag name.
        result = _name.parse(chars, nextIndex);
        if (result.isInvalid()) {
            return result;
        }
        nextIndex = result.index();

        // Parse optional space + attributes token.
        result = _space.parse(chars, nextIndex);
        if (result.isValid()) {
            result = _attributes.parse(chars, result.index());
            if (result.isValid()) {
                nextIndex = result.index();
                _hasAttributes = true;
            }
        }

        // Parse whitespace.
        result = _whitespace.parse(chars, nextIndex);

        return result;
    }

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    @Override
    public String toString() {
        String prefix = _terminal.toString() + _name.toString();
        String attributes = _space.toString() + _attributes.toString();

        return _hasAttributes
            ? prefix + attributes + _whitespace.toString()
            : prefix + _whitespace.toString();
    }

    public String getName() {
        return _name.toString();
    }

    public void setName(String name) {
        _name.setText(name);
    }

    public List<AttributeToken> getAttributes() {
        return _attributes.getElements().stream()
            .map(NodeTuple::getFirst)
            .collect(Collectors.toList());
    }

    public String getSpace() {
        return _space.toString();
    }

    public String getWhitespace() {
        return _whitespace.toString();
    }

    public void setWhitespace(CharSequence whitespace) {
        _whitespace.setWhitespace(whitespace);
    }

    public void setSpace(CharSequence whitespace) {
        _space.setSpace(whitespace);
    }

    @Override
    public TagHeader deepCopy() {
        TagHeader copy = new TagHeader();
        copy.setData(this);

        return copy;
    }

    @Override
    public void reset() {
        _name.reset();
        _space.reset();
        _attributes.getElements().clear();
        _whitespace.setWhitespace("");
    }

    @Override
    public void setData(TagHeader other) {
        reset();

        _name.setData(other._name);
        _space.setSpace(other._space);
        _attributes.setData(other._attributes);
        _whitespace.setWhitespace(other._whitespace.toString());

        _hasAttributes = other._hasAttributes;

        if (!equals(other)) {
            // TODO: Turn into unit test.
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TagHeader)) return false;
        TagHeader other = (TagHeader) obj;
        return Objects.equals(_name, other._name) &&
                Objects.equals(_space, other._space) &&
                Objects.equals(_attributes, other._attributes) &&
                Objects.equals(_hasAttributes, other._hasAttributes) &&
                Objects.equals(_whitespace, other._whitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _space, _attributes, _whitespace, _hasAttributes);
    }
}
