package parsing.model;

import java.util.Objects;

/**
 * @author Patrick Plieschnegger
 * TODO: Similarly, this should work for char sequences.
 * -> ConsumerNode, ContentToken
 */
public class ConditionalNode<N extends NodeSequence<N> & CopyNode<N>> extends AbstractParseNode {
    private final N _node;
    private final NodeCondition<N> _condition;

    public ConditionalNode(N node, NodeCondition<N> condition) {
        this._node = node;
        _condition = condition;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        ParseResult result = _node.parse(chars, index);
        if (result.isInvalid()) return result;

        if (!_condition.test(_node)) {
            int endIndex = result.index();
            String message = _condition.errorMessage();
            return ParseResult.invalid(endIndex, message, this);
        }

        return result;
    }

    public String conditionName() {
        return _condition.toString();
    }

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    @Override
    public String toString() {
        return _node.toString();
    }

    /**
     * @return a copy of the current node and all of its containing children.
     */
    @Override
    public ParseNode deepCopy() {
        return new ConditionalNode<>(_node, _condition);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConditionalNode)) return false;
        var other = (ConditionalNode<?>) obj;

        return Objects.equals(_node, other._node)
            && Objects.equals(conditionName(), other.conditionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_node, conditionName());
    }
}
