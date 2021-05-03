package parsing.model;

import essentials.collections.IterableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;

/**
 * This interface is implemented by Nodes with an unspecified number of sub-elements.
 * This interface allows to access the underlying elements.
 */
public interface NodeSequence<T> extends IterableList<T> {

    int size();

    T nodeAt(int index);

    /**
     * @return The list of elements
     */
    List<T> getElements();

    @Override
    default @NotNull ListIterator<T> listIterator() {
        return getElements().listIterator();
    }

    default boolean isEmpty() {
        return size() != 0;
    }
}
