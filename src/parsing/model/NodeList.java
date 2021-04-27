package parsing.model;

import essentials.collections.IterableList;

import java.util.List;

/**
 * @author Patrick Plieschnegger
 */
public interface NodeList<T extends CopyNode<T>> extends IterableList<T> {

    boolean isValid();

    int size();

    T nodeAt(int index);

    List<T> getElements();

    default boolean isEmpty() {
        return size() != 0;
    }

}
