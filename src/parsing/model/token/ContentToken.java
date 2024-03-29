package parsing.model.token;

import parsing.model.node.ContentNode;
import parsing.model.basic.CopyNode;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * A class-final value-based version of ContentNode which implements CopyNode.
 */
public final class ContentToken extends ContentNode implements CopyNode<ContentToken> {

    public ContentToken(String suffix) {
        super(suffix);
    }

    public ContentToken(String prefix, String postfix) {
        super(prefix, postfix);
    }

    @Override
    public ContentToken deepCopy() {
        ContentToken copy = new ContentToken(_prefix, _postfix);
        copy.setContent(_buffer);

        return copy;
    }

    @Override
    public void setData(ContentToken other) {
        super.setData(other);
    }
}
