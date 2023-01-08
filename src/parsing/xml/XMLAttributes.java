package parsing.xml;

import parsing.model.basic.CopyNode;
import parsing.model.node.MultiNode;
import parsing.model.util.NodeTuple;
import parsing.model.token.WhitespaceToken;

import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: (Attribute Whitespace)*
 */
public final class XMLAttributes extends MultiNode<NodeTuple<AttributeToken, WhitespaceToken>> implements CopyNode<XMLAttributes> {

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

    @Override
    public void reset() {
        super.reset();
    }
}
