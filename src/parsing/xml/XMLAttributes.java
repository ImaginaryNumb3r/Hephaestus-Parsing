package parsing.xml;

import parsing.model.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: Space (Attribute Whitespace)+
 */
public final class XMLAttributes extends OneOrMoreNode<NodeTuple<AttributeToken, WhitespaceToken>> implements CopyNode<XMLAttributes> {
    private final SpaceToken _space;

    // TODO: Reverse Order -> Whitespace and then AttributeToken.
    // What if we Had "OneOrMore" instead of MultiNode?
    protected XMLAttributes() {
        super(() -> new NodeTuple<>(new AttributeToken(), new WhitespaceToken()));
        _space = new SpaceToken();
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult parse = _space.parse(chars, index);
        if (parse.isInvalid()) {
            return parse;
        }
        int nextIndex = parse.index();

        return super.parseImpl(chars, nextIndex);
    }

    public List<AttributeToken> attributes() {
        return _elements.stream()
            .map(NodeTuple::getFirst)
            .collect(Collectors.toList());
    }

    @Override
    public XMLAttributes deepCopy() {
        XMLAttributes copy = new XMLAttributes();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(XMLAttributes other) {
        _elements.clear();
        if (!other._space.isEmpty()) {
            _space.setData(other._space);
        }

        var elementsCopy = other._elements.stream()
                .map(NodeTuple::deepCopy)
                .collect(Collectors.toList());
        _elements.addAll(elementsCopy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLAttributes)) return false;
        if (!super.equals(o)) return false;
        XMLAttributes that = (XMLAttributes) o;
        return Objects.equals(_space, that._space) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _space);
    }

    @Override
    public String toString() {
        return _space + super.toString();
    }
}
