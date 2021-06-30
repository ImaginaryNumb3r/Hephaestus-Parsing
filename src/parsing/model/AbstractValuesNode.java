package parsing.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Values with a separator between each value.
 */
public class AbstractValuesNode<V extends CopyNode<V>, S extends CopyNode<S>>
    extends AbstractParseNode {
    protected final Supplier<V> _valueConstructor;
    protected final Supplier<S> _separatorConstructor;
    protected final List<V> _values;
    protected final List<S> _separators;

    public AbstractValuesNode(
        Supplier<V> valueConstructor,
        Supplier<S> separatorConstructor
    ) {
        _valueConstructor = valueConstructor;
        _separatorConstructor = separatorConstructor;
        _values = new ArrayList<>();
        _separators = new ArrayList<>();
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        V valueToken = _valueConstructor.get();
        ParseResult result = valueToken.parse(chars, index);
        if (result.isInvalid()){
            ParseResult parseResult = ParseResult.at(index);
            parseResult.innerErrors().addAll(result.innerErrors());
            return parseResult;
        }

        _values.clear();
        _separators.clear();

        _values.add(valueToken);
        int nextIndex = result.index();

        while (true) {
            S separatorToken = _separatorConstructor.get();
            result = separatorToken.parse(chars, nextIndex);
            if (result.isInvalid()){
                ParseResult parseResult = ParseResult.at(nextIndex);
                parseResult.innerErrors().addAll(result.innerErrors());
                return parseResult;
            }
            nextIndex = result.index();

            valueToken = _valueConstructor.get();
            result = valueToken.parse(chars, nextIndex);
            if (result.isInvalid()){
                ParseResult parseResult = ParseResult.at(nextIndex);
                parseResult.innerErrors().addAll(result.innerErrors());
                return parseResult;
            }
            nextIndex = result.index();

            _separators.add(separatorToken);
            _values.add(valueToken);
        }
    }

    public void reset() {
        _values.clear();
        _separators.clear();
    }

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    @Override
    public String toString() {
        if (_values.isEmpty()) return "";

        var builder = new StringBuilder();
        Iterator<V> valuesIter = _values.iterator();
        Iterator<S> separatorIter = _separators.iterator();

        builder.append(valuesIter.next());

        while (valuesIter.hasNext()) {
            builder.append(separatorIter.next());
            builder.append(valuesIter.next());
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractValuesNode)) {
            return false;
        }
        AbstractValuesNode<V, S> other = (AbstractValuesNode<V, S>) obj;

        return Objects.equals(_values, other._values)
            && Objects.equals(_separators, other._separators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_values, _separators);
    }

    @Override
    public AbstractValuesNode<V, S> deepCopy() {
        AbstractValuesNode<V, S> copy = new AbstractValuesNode<>(_valueConstructor, _separatorConstructor);
        copy.setData(this);

        return copy;
    }

    public void setData(AbstractValuesNode<V, S> other) {
        _values.clear();
        _separators.clear();

        _values.addAll(other._values);
        _separators.addAll(other._separators);
    }
}
