package parsing.model;

import essentials.functional.Accumulator;

/**
 * @author Patrick Plieschnegger
 */
@FunctionalInterface
public interface CharAccumulator extends Accumulator<Character, StringBuilder> {
    StringBuilder apply(char var1, StringBuilder var2) throws RuntimeException;

    default StringBuilder apply(Character var1, StringBuilder var2) throws RuntimeException {
        char ch = var1;

        return apply(ch, var2);
    }

    default StringBuilder noOp(char acc, StringBuilder StringBuilder) {
        return StringBuilder;
    }
}
