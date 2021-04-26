package parsing.xml;

import parsing.model.*;

import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: (Attribute Whitespace)+
 */
public final class XMLAttributes extends OneOrMoreNode<NodeTuple<AttributeToken, WhitespaceToken>> implements CopyNode<XMLAttributes> {

    // TODO: Reverse Order -> Whitespace and then AttributeToken.
    // What if we Had "OneOrMore" instead of MultiNode?
    protected XMLAttributes() {
        super(() -> new NodeTuple<>(new AttributeToken(), new WhitespaceToken()));
    }

    @Override
    public XMLAttributes deepCopy() {
        var elements = _elements.stream()
                .map(CopyNode::deepCopy)
                .collect(Collectors.toList());

        XMLAttributes copy = new XMLAttributes();
        copy._elements.addAll(elements);

        return copy;
    }

    @Override
    public void setData(XMLAttributes other) {
        _elements.clear();

        var elementsCopy = other._elements.stream()
                .map(NodeTuple::deepCopy)
                .collect(Collectors.toList());
        _elements.addAll(elementsCopy);
    }
}
