package parsing.model.token;

import parsing.model.basic.CopyNode;
import parsing.model.basic.OptionalConsumer;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of no or arbitrary whitespace characters.
 */
public final class WhitespaceToken extends OptionalConsumer implements CopyNode<WhitespaceToken> {

    public WhitespaceToken() {
        super(Character::isWhitespace);
    }

    public void setWhitespace(CharSequence whitespace){
        if (!isBlank(whitespace)) {
            throw new IllegalArgumentException("Provided string must be a whitespace for whitespace tokens");
        }

        reset();
        _buffer.append(whitespace);
    }

    @Override
    public WhitespaceToken deepCopy() {
        WhitespaceToken copy = new WhitespaceToken();
        copy.setWhitespace(this);

        return copy;
    }

    @Override
    public void setData(WhitespaceToken other) {
        setWhitespace(other);
    }

    @Override
    public void reset() {
        _buffer.setLength(0);
    }
}
