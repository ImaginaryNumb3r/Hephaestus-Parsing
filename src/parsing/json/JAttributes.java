package parsing.json;

import parsing.model.basic.CopyNode;
import parsing.model.node.MultiNode;
import parsing.model.node.SequenceNode;
import parsing.model.token.WhitespaceToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: Whitespace JAttribute ( Whitespace ',' JAttribute )*
 */
/*package*/ final class JAttributes extends SequenceNode implements CopyNode<JAttributes> {
    private final JAttribute _first;
    private final MultiNode<IntermediateValue<JAttribute>> _values;

    public JAttributes() {
        _first = new JAttribute();
        _values = new MultiNode<>(() -> new IntermediateValue<>(JAttribute::new));

        _sequence.addAll(asList(new WhitespaceToken(), _first, _values));
    }

    @Override
    public void setData(JAttributes other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JAttributes deepCopy() {
        JAttributes other = new JAttributes();
        other.setData(this);

        return other;
    }

    public List<JAttribute> getAttributes() {
        List<JAttribute> values = new ArrayList<>();
        values.add(_first);

        _values.stream()
                .map(IntermediateValue::getValue)
                .forEach(values::add);

        return values;
    }

    public Stream<JAttribute> stream() {
        return getAttributes().stream();
    }
}
