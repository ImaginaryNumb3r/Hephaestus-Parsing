package parsing.model;

import java.util.Objects;

/**
 * @author Patrick Plieschnegger
 */
public class Epsilon extends AbstractParseNode implements CopyNode<Epsilon> {
    private static final Epsilon _instance = new Epsilon();

    private Epsilon() { }

    public static Epsilon instance() {
        return _instance;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        return ParseResult.at(index);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Epsilon deepCopy() {
        return this;
    }

    @Override
    public void setData(Epsilon other) {
        // Noop.
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}