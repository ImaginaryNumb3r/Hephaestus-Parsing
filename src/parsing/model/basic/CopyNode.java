package parsing.model.basic;

import parsing.model.util.ParseNode;

import java.util.function.Supplier;

/**
 * @author Patrick Plieschnegger
 * @implNote The implementing class should keep the type parameter generic unless it is final.
 *           This must be because Java has no "self" type and the return type of "deepCopy" cannot be changed
 *           statically for classes which further extend the implementing class.
 */
public interface CopyNode<T extends ParseNode> extends ParseNode {

    /**
     * Implementing this interface statically guarantees that its deep copy is its own dynamic type (and not just ParseNode)
     * @return Value-equal instance to the source instance.
     */
    T deepCopy();

    void setData(T other);

    void reset();

    /**
     * Convenience method for creating a copy of a node, utilizing {@link this#setData} and a constructor.
     * @param node the node to be copied
     * @param constructor the constructor for the newly created node
     * @return copy of {@param node}
     * @throws IllegalArgumentException if {@param constructor} returns a reference to the object {@param node}
     */
    static <T extends CopyNode<T>> T deepCopy(T node, Supplier<T> constructor) {
        T copy = constructor.get();
        if (copy == node) {
            throw new IllegalArgumentException("Supplier for copying nodes must not return an instance to an existing node.");
        }

        copy.setData(node);
        return copy;
    }

}
