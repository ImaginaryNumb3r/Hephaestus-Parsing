package parsing.model;

/**
 * @author Patrick Plieschnegger
 * @implNote The implementing class should kept the type parameter generic unless it is final.
 *           This must be because Java has no "self" type and the return type of "deepCopy" cannot be changed
 *           statically for classes which further extend the implementing class.
 */
public interface CopyNode<T extends ParseNode> extends ParseNode {

    /**
     * Implementing this interface statically guarantees that its deep copy is its own dynamic type (and not just ParseNode)
     * @return Value-equal instance to the source instance.
     */
    T deepCopy();

    // TODO: Determine if this method is really necessary. Right now, it makes problems with SequenceNodes.
    void setData(T other);

}
