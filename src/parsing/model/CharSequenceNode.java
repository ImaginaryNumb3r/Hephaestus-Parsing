package parsing.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * An interface of convenience for Nodes which implement CharSequence and NodeSequence.
 * This removes redundancy.
 */
public interface CharSequenceNode extends CharSequence, NodeSequence<Character> {

    default int size() {
        return length();
    }

    @Override
    default Character nodeAt(int index) {
        return charAt(index);
    }

    @Override
    default List<Character> getElements() {
        return chars()
            .mapToObj(intVal -> (char) intVal)
            .collect(Collectors.toList());
    }
}
