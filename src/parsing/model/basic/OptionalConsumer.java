package parsing.model.basic;

import org.jetbrains.annotations.NotNull;
import parsing.model.util.CharPredicate;
import parsing.model.util.ParseNode;
import parsing.model.util.ParseResult;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true (even the empty string).
 * Also terminates if the end of the input was reached.
 * This class makes no assumptions about the minimum or maximum length it will parse.
 *
 * @implNote does not extend AbstractParseNode since its built-in range check would violate the empty string policy of this Consumer.
 *           Since an empty string at the end of the document is possible and likely.
 */
public class OptionalConsumer implements ParseNode, CharSequence {
    protected final StringBuilder _buffer;
    protected final CharPredicate _acceptCondition;

    protected OptionalConsumer(CharPredicate acceptCondition) {
        _buffer = new StringBuilder();
        _acceptCondition = acceptCondition;
    }

    @Override
    public ParseResult parse(String chars, final int start) {
        // To parse an empty string at the end of the document. The consumer state then defaults to empty-string.
        if (chars.length() == start) {
            return ParseResult.at(start);
        }

        int end = start;

        char ch = chars.charAt(start);
        while (_acceptCondition.test(ch) && ++end < chars.length()) {
            ch = chars.charAt(end);
        }

        String slice = chars.substring(start, end);
        _buffer.append(slice);

        return ParseResult.at(end);
    }

    @Override
    public String asString() {
        return toString();
    }

    @Override
    public int length() {
        return _buffer.length();
    }

    @Override
    public char charAt(int index) {
        return _buffer.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return _buffer.subSequence(start, end);
    }

    @NotNull
    @Override
    public String toString() {
        return _buffer.toString();
    }

    public OptionalConsumer deepCopy() {
        var copy = new OptionalConsumer(_acceptCondition);
        copy._buffer.append(_buffer);

        return copy;
    }

    /**
     * The accepting condition is not copied because this method only sets the data.
     * It does not change the behavior of the object.
     * @param other
     */
    protected void setData(OptionalConsumer other) {
        reset();
        _buffer.append(other);
    }

    protected void reset() {
        _buffer.setLength(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionalConsumer)) return false;
        OptionalConsumer that = (OptionalConsumer) o;

        // Remark: Lambdas cannot be compared by value.
        return Objects.equals(_buffer.toString(), that._buffer.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString(), _acceptCondition);
    }

    /**
     * @param sequence the String or CharSequence to be checked.
     * @return true if all of the characters are whitespaces.
     */
    protected boolean isBlank(CharSequence sequence) {
        for (int i = 0; i != sequence.length(); ++i) {
            char ch = sequence.charAt(i);

            boolean isWhitespace = Character.isWhitespace(ch);
            if (!isWhitespace) {
                return false;
            }
        }

        return true;
    }
}
