package parsing.xml;

import parsing.model.node.ContentNode;
import parsing.model.basic.CopyNode;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: '<!--' "Text" '--/>'
 */
public final class CommentToken extends ContentNode implements CopyNode<CommentToken> {
    public static String COMMENT_START = "<!--";
    public static String COMMENT_END = "-->";

    public CommentToken() {
        super(COMMENT_START, COMMENT_END);
    }

    @Override
    public CommentToken deepCopy() {
        CommentToken copy = new CommentToken();
        copy._buffer.append(_buffer);

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    public void setData(CommentToken other) {
        reset();
        _buffer.append(other.getContent());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CommentToken && super.equals(o);
    }

    @Override
    public int hashCode() {
        // Permutate
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return COMMENT_START + _buffer + COMMENT_END;
    }
}
