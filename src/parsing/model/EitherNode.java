package parsing.model;

import essentials.util.Nulls;

import java.util.Objects;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: O | M
 * @apiNote must always parse either O or M to have valid state.
 * TODO: Make simple unit tests
 */
public class EitherNode<O extends CopyNode<O>, M extends CopyNode<M>> extends AbstractParseNode {
    protected final O _optional;
    protected final M _mandatory;
    protected Status _status;

    public EitherNode(O optional, M mandatory) {
        _optional = optional;
        _mandatory = mandatory;
        _status = Status.NONE;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        var result = _optional.parse(chars, index);

        if (result.isValid()) {
            _status = Status.OPTIONAL;
        } else {
            ParseResult mandatory = _mandatory.parse(chars, index);

            if (mandatory.isValid()) {
                _status = Status.MANDATORY;
                result = mandatory;
            } else {
                result = ParseResult.invalid(index, "Could not consume either node", this, result, mandatory);
            }
        }

        return result;
    }

    public boolean hasFirst() {
        return _status == Status.OPTIONAL;
    }

    protected Optional<O> first() {
        return _status == Status.OPTIONAL
                ? Optional.of(_optional)
                : Optional.empty();
    }

    public boolean hasSecond() {
        return _status == Status.MANDATORY;
    }

    protected Optional<M> second() {
        return _status == Status.MANDATORY
                ? Optional.of(_mandatory)
                : Optional.empty();
    }

    public CopyNode<?> getValue() {
        return _status == Status.OPTIONAL ? _optional : _mandatory;
    }

    @Override
    public String toString() {
        return _status == Status.OPTIONAL  ? _optional.toString()
             : _status == Status.MANDATORY ? _mandatory.toString()
             : "";
    }

    @Override
    public EitherNode<O, M> deepCopy() {
        EitherNode<O, M> copy = new EitherNode<>(_optional.deepCopy(), _mandatory.deepCopy());
        copy.setData(this);

        return copy;
    }

    public void setData(EitherNode<O, M> other) {
        if (_mandatory != null) {
            _mandatory.setData(other._mandatory);
        }
        if (_optional != null) {
            _optional.setData(other._optional);
        }

        _status = other._status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EitherNode)) return false;
        EitherNode<?, ?> that = (EitherNode<?, ?>) o;
        return Objects.equals(_optional, that._optional) &&
                Objects.equals(_mandatory, that._mandatory) &&
                _status == that._status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_optional, _mandatory, _status);
    }

    protected enum Status {
        OPTIONAL, MANDATORY, NONE
    }
}
