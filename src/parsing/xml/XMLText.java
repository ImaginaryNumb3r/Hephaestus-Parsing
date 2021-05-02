package parsing.xml;

import essentials.annotations.Package;
import parsing.model.ContentNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * The text that can be between nodes.
 */
public final class XMLText extends ContentNode implements CopyNode<XMLText> {
    private static final String POSTFIX = "<";

    public XMLText() {
        super("", POSTFIX);
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult result = super.parseImpl(chars, index);
        if (result.isInvalid()) return result;

        // Revert lookahead of postfix.
        result = ParseResult.at(result.index() - POSTFIX.length());
        var next = result;

        // HACK: Also append comments. The grammar does not support it yet.
        while (next.isValid()) {
            CommentToken comment = new CommentToken();
            next = comment.parse(chars, next.index());

            if (next.isValid()) {
                _buffer.append(comment);
                result = next;

                XMLText text = new XMLText();
                next = text.parse(chars, next.index());

                if (next.isValid()) {
                    _buffer.append(text);
                    result = next;
                }
            }
        }

        return result;
    }

    @Package String getRaw() {
        return _buffer.toString() + POSTFIX;
    }

    @Override
    public String toString() {
        // Do not print postfix because it only serves as a termination marker.
        return _buffer.toString();
    }

    public String rawString() {
        return toString() + POSTFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLText)) return false;
        XMLText that = (XMLText) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString());
    }

    @Override
    public XMLText deepCopy() {
        XMLText copy = new XMLText();
        copy.setContent(_buffer);

        return copy;
    }

    @Override
    public void setData(XMLText other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
