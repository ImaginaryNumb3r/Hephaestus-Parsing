package parsing.model;

import java.util.function.Predicate;

/**
 * @author Patrick Plieschnegger
 */
public interface NodeCondition<N extends CopyNode<N>> extends Predicate<NodeList<N>> {

    boolean test(NodeList<N> node);

    String toString();

    default String errorMessage() {
        return "Postcondition \"" + toString() + "\" failed.";
    }

    static <N extends CopyNode<N>> NodeCondition<N>
        satisfying(Predicate<NodeList<N>> predicate, String name)
    {
        return new NodeCondition<>() {
            @Override
            public boolean test(NodeList<N> node) {
                return predicate.test(node);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    class OneOrMore<N extends CopyNode<N>> implements NodeCondition<N> {

        @Override
        public boolean test(NodeList<N> node) {
            return !node.isEmpty();
        }

        @Override
        public String toString() {
            return "One or More";
        }
    }

    class ZeroOrOne<N extends CopyNode<N>> implements NodeCondition<N> {

        @Override
        public boolean test(NodeList<N> node) {
            return node.size() <= 1;
        }

        @Override
        public String toString() {
            return "Zero or More";
        }
    }
}
