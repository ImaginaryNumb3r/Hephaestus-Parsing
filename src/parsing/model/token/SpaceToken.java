package parsing.model.token;

import collections.iterator.Iterables;
import parsing.model.basic.CopyNode;
import parsing.model.basic.OptionalConsumer;
import parsing.model.util.ParseResult;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of whitespaces that is at least of size 1.
 * @apiNote The object can never have zero characters at any given point.
 */
public final class SpaceToken extends OptionalConsumer implements CopyNode<SpaceToken> {
    private static final char DEFAULT_WHITESPACE = ' ';

    public SpaceToken() {
        this(DEFAULT_WHITESPACE);
    }

    private SpaceToken(char whitespace) {
        super(Character::isWhitespace);
        _buffer.append(whitespace);
    }

    private SpaceToken(CharSequence whitespace) {
        super(Character::isWhitespace);
        _buffer.append(whitespace);
    }

    @Override
    public ParseResult parse(String chars, int start) {
        char mandatoryChar = chars.charAt(start);

        if (!_acceptCondition.test(mandatoryChar)) {
            String message = "Cannot parse mandatory char for Space token: \"" + mandatoryChar + "\"";
            return ParseResult.invalid(start, message);
        }
        _buffer.setCharAt(0, mandatoryChar);
        ++start;

        return super.parse(chars, start);
    }

    public void setSpace(char whitespace){
        setSpace(String.valueOf(whitespace));
    }

    public void setSpace(CharSequence whitespace){
        if (whitespace.length() == 0)
            throw new IllegalArgumentException("Provided string must not be empty for space tokens");

        if (!isBlank(whitespace))
            throw new IllegalArgumentException("Provided string must be a whitespace for space tokens");

        /*
         * IMPORTANT: We can't simply set he length to 0 and append the sequence
         * Doing so would violate the invariants and expose faulty state via concurrency.
         */
        _buffer.setLength(whitespace.length());

        int i = 0;
        for (char ch : Iterables.of(whitespace)) {
            _buffer.setCharAt(i, ch);
            ++i;
        }
    }

    @Override
    public void setData(SpaceToken other) {
        reset();
        setSpace(other._buffer);
    }

    /**
     * This will reset the object to have a single whitespace.
     * The content length cannot be set to zero because this violates the invariant of the class.
     * @apiNote this method is not thread save as the invariants of this class are violated.
     */
    @Override
    public void reset() {
        setSpace(DEFAULT_WHITESPACE);
    }

    @Override
    public SpaceToken deepCopy() {
        var copy = new SpaceToken();

        char mandatoryChar = _buffer.charAt(0);
        copy._buffer.setCharAt(0, mandatoryChar);

        if (_buffer.length() > 1) {
            CharSequence remainingChars = _buffer.subSequence(1, _buffer.length());
            copy._buffer.append(remainingChars);
        }

        return copy;
    }

    /**
     *
     * @param whitespace
     * @return
     * @throws IllegalArgumentException
     */
    public static SpaceToken of(CharSequence whitespace) throws IllegalArgumentException {
        Iterables.of(whitespace).forEach(ch -> {
            if (!Character.isWhitespace(ch)) {
                throw new IllegalArgumentException();
            }
        });

        return new SpaceToken(whitespace);
    }
}
