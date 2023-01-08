package parsing.xml;

import parsing.model.basic.CopyNode;
import parsing.model.node.MultiNode;
import parsing.model.util.NodeTuple;
import parsing.model.token.WhitespaceToken;

import java.util.stream.Collectors;

/**
 * @author Patrick Plieschnegger
 */
public final class XMLComments extends MultiNode<NodeTuple<CommentToken, WhitespaceToken>> implements CopyNode<XMLComments> {

    public XMLComments() {
        super( () -> new NodeTuple<>(new CommentToken(), new WhitespaceToken()) );
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public XMLComments deepCopy() {
        var elementsCopy = super.deepCopy().getElements();
        XMLComments comments = new XMLComments();
        comments._elements.addAll(elementsCopy);

        return comments;
    }

    @Override
    public void setData(XMLComments other) {
        reset();

        var elementsCopy = other._elements.stream()
            .map(CopyNode::deepCopy)
            .collect(Collectors.toList());

        _elements.addAll(elementsCopy);
    }
}
