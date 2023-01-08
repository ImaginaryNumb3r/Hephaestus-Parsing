package parsing.model.terminal;

import parsing.model.basic.AbstractParseNode;
import parsing.model.basic.CopyNode;
import parsing.model.util.ParseResult;

public class Epsilon extends AbstractParseNode implements CopyNode<Epsilon> {
    private static final Epsilon _instance = new Epsilon();

    private Epsilon() { }

    public static Epsilon instance() {
        return _instance;
    }

    protected ParseResult parseImpl(String chars, int index) {
        return ParseResult.at(index);
    }

    public String toString() {
        return "";
    }

    public Epsilon deepCopy() {
        return this;
    }

    public void setData(Epsilon other) {
        // No op
    }

    @Override
    public void reset() {
        // No op
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }
}
