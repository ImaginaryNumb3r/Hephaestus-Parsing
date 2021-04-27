package parsing.model;

import essentials.collections.IterableList;

import java.util.List;

/**
 * @author Patrick Plieschnegger
 * TODO: Turn into Predicate that can be used for Conditionals
 */
public interface OneOrMore<T extends CopyNode<T>> extends NodeList<T> {

    default boolean isValid() {
        return size() != 0;
    }
}
