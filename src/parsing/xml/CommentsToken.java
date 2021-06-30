package parsing.xml;

import parsing.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 08.06.2021
 * Grammar: '<!--' "Text" '--/>'
 * Grammar: '<!--' ("Text") | (CommentToken) '--/>'
 */
public final class CommentsToken extends AbstractValuesNode<CommentToken, WhitespaceToken>
    implements CopyNode<CommentsToken> { //

    public CommentsToken() {
        super(CommentToken::new, WhitespaceToken::new);
    }

    @Override
    public CommentsToken deepCopy() {
        CommentsToken copy = new CommentsToken();
        copy.setData(this);

        return copy;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        ParseResult result = super.parseImpl(chars, index);

        if (_values.isEmpty())
            return ParseResult.invalid(index, "Cannot parse Comments", this);

        return result;
    }

    @Override
    public void setData(CommentsToken other) {
        _values.clear();
        _separators.clear();

        var valuesCopy = other._values.stream()
            .map(CommentToken::deepCopy)
            .collect(Collectors.toList());

        var separatorsCopy = other._separators.stream()
            .map(WhitespaceToken::deepCopy)
            .collect(Collectors.toList());

        _values.addAll(valuesCopy);
        _separators.addAll(separatorsCopy);
    }
}
