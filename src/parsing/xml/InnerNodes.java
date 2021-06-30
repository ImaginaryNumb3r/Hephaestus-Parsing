package parsing.xml;

import essentials.util.Nulls;
import parsing.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Grammar: ( XMLNode )* | XMLText
 * In theory, this could become an EitherNode<MultiNode<XMLNode>, XMLText> but do we want to?
 */
public final class InnerNodes extends MultiNode<XMLNode> implements CopyNode<InnerNodes> {
    private final EitherNode< XMLText, CommentsToken> _content;
    private Status _status;

    public InnerNodes() {
        super(XMLNode::new);
        _content = new EitherNode<>(new XMLText(), new CommentsToken());
        _status = Status.NONE;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult result = super.parseImpl(chars, index);

        // If the next index is the same as the input index, we know nothing could be parsed.
        if (index != result.index()) {
            _status = Status.NODE;
        }
        // Fallback to parsing the node value as a string.
        else {
            ParseResult fallback = _content.parse(chars, index);
            if (fallback.isInvalid()) return ParseResult.invalid(index, "TODO", this, result, fallback);

            result = fallback;
            _status = Status.TEXT;
        }

        return result;
    }

    @Override
    public InnerNodes deepCopy() {
        InnerNodes copy = new InnerNodes();
        copy.setData(this);

        if (!copy.equals(this)) {
            throw new IllegalStateException();
        }

        return copy;
    }

    @Override
    public void setData(InnerNodes other) {
        reset();
        _content.setData(other._content);

        var elementsCopy = other._elements.stream()
                .map(XMLNode::deepCopy)
                .collect(Collectors.toList());

        _elements.addAll(elementsCopy);
        _status = other._status;
    }

    public boolean isText() {
        return _status == Status.TEXT;
    }

    public boolean hasInnerNodes() {
        return _status == Status.NODE;
    }

    public Optional<String> getData() {
        return Nulls.box(isText(), _content.toString());
    }

    public Optional<List<XMLNode>> innerNodes() {
        return Nulls.box(hasInnerNodes(), _elements);
    }

    @Override
    public void reset() {
        _content.first().ifPresent(XMLText::reset);
        _content.second().ifPresent(CommentsToken::reset);

        _elements.clear();
    }

    @Override
    public String toString() {
        return _status == Status.TEXT ? _content.toString()
                : _status == Status.NODE ? super.toString()
                : "";
    }

    private enum Status {
        TEXT, NODE, NONE
    }
}
