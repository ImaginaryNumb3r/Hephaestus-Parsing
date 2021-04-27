package parsing.xml;

import parsing.model.*;

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

    @Override
    public XMLAttributes deepCopy() {
        var elements = _elements.stream()
                .map(CopyNode::deepCopy)
                .collect(Collectors.toList());

        XMLAttributes copy = new XMLAttributes();
        copy._space.setData(_space);
        copy._elements.addAll(elements);

        return copy;
    }

    @Override
    public void setData(XMLAttributes other) {
        _elements.clear();
        _space.setData(other._space);

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
