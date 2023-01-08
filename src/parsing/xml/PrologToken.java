package parsing.xml;

import parsing.model.node.ContentNode;
import parsing.model.basic.CopyNode;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * TODO: Also consume Attributes (similar to ClosedTag)
 */
public final class PrologToken extends ContentNode implements CopyNode<PrologToken> {

    public PrologToken() {
        super("<?", "?>");
    }

    public PrologToken deepCopy() {
        PrologToken copy = new PrologToken();
        copy.setContent(_buffer);

        return copy;
    }

    @Override
    public void setData(PrologToken other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
