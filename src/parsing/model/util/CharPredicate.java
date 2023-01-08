package parsing.model.util;

import java.util.function.Predicate;

/**
 * Creator: Patrick Plieschnegger
 * A simple derivation of {@link java.util.function.Predicate } which accepts a character primitive.
 */
public interface CharPredicate extends Predicate<Character> {

    boolean test(char character);

    default boolean test(Character character) {
        return test((char) character);
    }
}
