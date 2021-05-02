package parsing.model;

import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 * TODO: Basic Test
 */
public class OptionalNode<N extends CopyNode<N>>
    extends EitherNode<N, Epsilon> implements CopyNode<OptionalNode<N>> {

    public OptionalNode(N optional) {
        super(optional, Epsilon.instance());
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        ParseResult result;
        try {
            result = super.parseImpl(chars, index);
            if (result.isInvalid()) {
                result = ParseResult.at(index);
            }
        } catch (TokenConditionException ex) {
            result = ParseResult.at(index);
        }

        return result;
    }

    public Optional<N> fetch() {
        return hasFirst() ? first() : Optional.empty();
    }

    public N get() {
        return fetch().orElse(null);
    }

    public boolean isPresent() {
        return hasFirst();
    }

    public void setData(N data) {
        super.setData(new OptionalNode<>(data));
    }

    @Override
    public void setData(OptionalNode<N> other) {
        switch (other._status) {
            case OPTIONAL:
                super.setData(other);
                break;
            case MANDATORY:
                _status = Status.MANDATORY;
                break;
            case NONE:
                _status = Status.NONE;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OptionalNode)) return false;
        OptionalNode<N> other = (OptionalNode<N>) obj;

        return (!isPresent() && !other.isPresent()) || super.equals(other);
    }

    @Override
    public OptionalNode<N> deepCopy() {
        OptionalNode<N> copy = new OptionalNode<>(_optional.deepCopy());
        copy._status = _status;

        return copy;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
