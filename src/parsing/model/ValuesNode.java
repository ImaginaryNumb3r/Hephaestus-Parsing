package parsing.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Values with a separator between each value.
 */
public class ValuesNode<V extends CopyNode<V>, S extends CopyNode<S>>
    extends AbstractParseNode implements CopyNode<ValuesNode<V, S>> {
    private final Supplier<V> _valueConstructor;
    private final Supplier<S> _separatorConstructor;
    private final List<V> _values;
    private final List<S> _separators;

    public ValuesNode(
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
        if (!(obj instanceof ValuesNode)) {
            return false;
        }
        ValuesNode<V, S> other = (ValuesNode<V, S>) obj;

        return Objects.equals(_values, other._values)
            && Objects.equals(_separators, other._separators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_values, _separators);
    }

    @Override
    public ValuesNode<V, S> deepCopy() {
        ValuesNode<V, S> copy = new ValuesNode<>(_valueConstructor, _separatorConstructor);
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(ValuesNode<V, S> other) {
        _values.clear();
        _separators.clear();

        _values.addAll(other._values);
        _separators.addAll(other._separators);
    }
}
