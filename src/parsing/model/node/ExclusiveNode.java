package parsing.model.node;

import parsing.model.basic.AbstractParseNode;
import parsing.model.basic.CopyNode;
import parsing.model.util.ParseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExclusiveNode<V extends CopyNode<?>, T> extends AbstractParseNode {
    private final List<V> _nodes;
    private final List<T> _types;
    private int _nodeIndex = INVALID;

    protected ExclusiveNode(List<V> nodes, List<T> types) {
        _nodes = nodes;
        _types = types;
        assertListSizes();
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        if (_nodeIndex != INVALID) {
            throw new IllegalStateException("Cannot reparse ExclusiveNode! (for now)");
        }
        assertListSizes();
        List<ParseResult> errorResults = new ArrayList<>();

        int counter = 0;
        for (V node : _nodes) {
            ParseResult result = node.parse(chars, index);
            if (result.isValid()) {
                _nodeIndex = counter;
                return result;
            }
            ++counter;
            errorResults.add(result);
        }

        return ParseResult.invalid(index, "Cannot parse any nodes in ExclusiveNodes", errorResults);
    }

    @Override
    public String toString() {
        return _nodeIndex == INVALID ? "unparsed" : _nodes.get(_nodeIndex).toString();
    }

    @Override
    public ExclusiveNode<V, T> deepCopy() {
        // Re-use reference to typs.
        List<?> copiedNodes = _nodes.stream().map(CopyNode::deepCopy).collect(Collectors.toList());
        return new ExclusiveNode<>((List<V>) copiedNodes, _types);
    }

    public T getType() {
        return _types.get(_nodeIndex);
    }

    public V getNode() {
        return _nodes.get(_nodeIndex);
    }

    public Class<? extends CopyNode<?>> getNodeClass() {
        return (Class<? extends CopyNode<?>>) getNode().getClass();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExclusiveNode) {
            var other = (ExclusiveNode<V, T>) obj;
            return _nodes.equals(other._nodes)
                    && _types.equals(other._types)
                    && _nodeIndex == other._nodeIndex;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_nodes, _types, _nodeIndex);
    }

    public void reset() {
        _nodes.forEach(node -> node.reset());
        _nodeIndex = INVALID;
    }

    private void assertListSizes() {
        if (_nodes.size() != _types.size()) {
            throw new IllegalArgumentException(String.format(
                    "Node and type list must have the same size in ExclusiveNode, but were %d and %d",
                    _nodes.size(), _types.size()));
        }
        if (_nodes.isEmpty()) {
            throw new IllegalArgumentException("ExclusiveNode must contain at least one value");
        }
    }
}
