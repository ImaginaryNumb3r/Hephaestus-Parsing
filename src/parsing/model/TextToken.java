package parsing.model;

import collections.iterator.Iterables;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Non-empty string token which must not contain whitespace.
 * @deprecated because of its opinionated nature, it is not useful for the use in a specific grammar
 */
@Deprecated
public final class TextToken extends ConsumerNode implements CopyNode<TextToken> {
    private static final Set<Character> ALLOWED_CHARS = Set.of('_', '.', ':', '-');
    // TODO: ConsumeOneOrManyNode
    // TODO: Should we have Terminal/Singleton, OneOrZero, OneOrMany, ZeroOrMany -> can never fail?

    public TextToken() { // TODO: Change to "not whitespace" -> character '!' could be making problems
        super(ch -> Character.isAlphabetic(ch) || Character.isDigit(ch) || ALLOWED_CHARS.contains(ch));

        /*

        super(ch -> !Character.isWhitespace(ch));

        setText(defaultText);*/

    }

    public void setText(CharSequence text) {
        if (text.length() == 0)
            throw new IllegalArgumentException("Provided string must not be empty for text tokens");

        if (isBlank(text))
            throw new IllegalArgumentException("Provided string must not be a whitespace for text tokens");

        /*
         * IMPORTANT: We can't simply set he length to 0 and append the sequence
         * Doing so would violate the invariants and expose faulty state via concurrency.
         */
        _buffer.setLength(text.length());

        int i = 0;
        for (char ch : Iterables.of(text)) {
            _buffer.setCharAt(i, ch);
            ++i;
        }
    }

    @Override
    public TextToken deepCopy() {
        return new TextToken();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(TextToken other) {
        super.setData(other);
    }

    /**
     * @return the parsed text as string.
     */
    @NotNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
