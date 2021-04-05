package parsing.json;

import parsing.model.CharTerminal;
import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * A value that is preceded by a comma and according whitespaces.
 * It can never be the first element in the series and is to be used in objects and arrays.
 * Grammar: ',' T
 */
/*package*/ final class IntermediateValue<T extends CopyNode<T>> extends SequenceNode implements CopyNode<IntermediateValue<T>> {
    private final Supplier<T> _valueConstructor;
    private final T _value;

    public IntermediateValue(Supplier<T> valueConstructor) {
        this(valueConstructor, valueConstructor.get());
    }

    public IntermediateValue(Supplier<T> valueConstructor, T defaultValue) {
        _valueConstructor = valueConstructor;
        _value = defaultValue;

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), new CharTerminal(','),
                        new WhitespaceToken(), _value
                )
        );
    }

    @Override
    public IntermediateValue<T> deepCopy() {
        IntermediateValue<T> other = new IntermediateValue<>(_valueConstructor);
        other.setData(this);

        return other;
    }

    public T getValue() {
        return _value;
    }

    @Override
    public void setData(IntermediateValue<T> other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
