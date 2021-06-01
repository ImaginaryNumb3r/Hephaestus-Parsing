package parsing.json;

import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * An array consists of a list of list of values separated by commas.
 * Grammar: ( '[' Whitespace ']' ) |
 *          ( '[' JValue ( ',' JValue)* ']' )
 */
public final class JArray extends SequenceNode implements CopyNode<JArray> {
    private final OptionalNode<JValues> _values;

    public JArray() {
        _values = new OptionalNode<>(new JValues());

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), new CharTerminal('['),
                        new WhitespaceToken(), _values,
                        new WhitespaceToken(), new CharTerminal(']')
                )
        );
    }

    public List<JValue> getValues() {
        return _values.fetch()
            .map(JValues::getValues)
            .orElse(new ArrayList<>());
    }

    public boolean isEmpty() {
        return !_values.isPresent();
    }

    public <T> ArrayList<T> toArray(Function<JValue, T> cast) {
        ArrayList<T> array = new ArrayList<>();
        for (JValue value : getValues()) {
            array.add(cast.apply(value));
        }

        return array;
    }

    @Override
    public JArray deepCopy() {
        JArray other = new JArray();
        other.setData(this);

        return other;
    }

    @Override
    public void setData(JArray other) {
        super.setData(other);
    }
}
