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
    extends AbstractValuesNode<V, S> implements CopyNode<ValuesNode<V, S>> {

    public ValuesNode(
        Supplier<V> valueConstructor,
        Supplier<S> separatorConstructor
    ) {
        super(valueConstructor, separatorConstructor);
    }

    @Override
    public ValuesNode<V, S> deepCopy() {
        ValuesNode<V, S> copy = new ValuesNode<>(_valueConstructor, _separatorConstructor);
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(ValuesNode<V, S> other) {
        super.setData(other);
    }
}
