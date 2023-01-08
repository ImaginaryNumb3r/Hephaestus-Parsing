package parsing.model.node;

import parsing.model.basic.AbstractParseNode;
import parsing.model.basic.CopyNode;
import parsing.model.util.ParseResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @param <T>
 */
public class SeparatedNodes<T extends CopyNode<T>, S extends CopyNode<S>> extends AbstractParseNode {
    protected final List<T> _elements;
    protected final List<S> _separators;
    protected final Supplier<T> _tokenConstructor;
    protected final Supplier<S> _separatorConstructor;

    public SeparatedNodes(Supplier<T> tokenConstructor, Supplier<S> separatorConstructor) {
        _elements = new ArrayList<>();
        _separators = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
        _separatorConstructor = separatorConstructor;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        T token = _tokenConstructor.get();
        ParseResult result = token.parse(chars, index);
        if (result.isInvalid()) {
            return result;
        }
        _separators.clear();
        _elements.clear();
        _elements.add(token);

        while (true) {
            int nextIndex = result.index();
            var separator = _separatorConstructor.get();
            var parsed = separator.parse(chars, nextIndex);
            if (parsed.isInvalid()) return result;

            nextIndex = parsed.index();
            token = _tokenConstructor.get();
            parsed = token.parse(chars, nextIndex);
            if (parsed.isInvalid()) return result;

            result = parsed;
            _separators.add(separator);
            _elements.add(token);
        }
    }

    /**
     * @return the list of elements and separators in sequentialOrder
     */
    public List<CopyNode<?>> getAllSequentially() {
        var nodes = new ArrayList<CopyNode<?>>();
        if (_elements.isEmpty()) return nodes;

        Iterator<S> sepIter = _separators.iterator();
        Iterator<T> tokenIter = _elements.iterator();

        // Add first if elements are not empty.
        nodes.add(tokenIter.next());

        while (sepIter.hasNext()) {
            nodes.add(sepIter.next());
            nodes.add(tokenIter.next());
        }

        return nodes;
    }

    public List<T> getElements() {
        return _elements.stream()
                        .map(CopyNode::deepCopy)
                        .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return getAllSequentially().stream()
                                   .map(Objects::toString)
                                   .collect(Collectors.joining());
    }

    @Override
    public SeparatedNodes<T, S> deepCopy() {
        var other = new SeparatedNodes<>(_tokenConstructor, _separatorConstructor);
        other.setData(this);

        return other;
    }

    public void setData(SeparatedNodes<T, S> other) {
        _elements.clear();
        _elements.addAll(other._elements);
        _separators.clear();
        _separators.addAll(other._separators);
    }

    public void reset() {
        _elements.clear();
        _separators.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeparatedNodes) {
            SeparatedNodes<T, S> other = (SeparatedNodes<T, S>) obj;
            return _elements.equals(other._elements);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_elements, _separators);
    }
}
