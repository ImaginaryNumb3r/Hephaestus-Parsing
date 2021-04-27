package parsing.model;

import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 */
public class OptionalNode<N extends CopyNode<N>> extends EitherNode<N, Epsilon> {

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

    protected void setData(N data) {
        super.setData(new OptionalNode<>(data));
    }

    @Override
    public void setData(EitherNode<N, Epsilon> other) {
        if (other.hasFirst()) {
            super.setData(other);
        }
    }

    @Override
    public boolean equals(Object o) {
        // Don't compare if the
        return !isPresent() || super.equals(o);
    }
}
