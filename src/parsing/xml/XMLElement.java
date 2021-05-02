package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;
import parsing.model.WhitespaceToken;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Patrick Plieschnegger
 * Grammar: XMLStartTag (InnerNodes Whitespace XMLCloseTag)
 */
public class XMLElement extends AbstractParseNode implements CopyNode<XMLElement> {
    private final XMLStartTag _start;
    private InnerNodes _nodes;
    private WhitespaceToken _whitespace;
    private XMLCloseTag _close;

    public XMLElement() {
        _start = new XMLStartTag();
        _nodes = null;
        _whitespace = null;
        _close = null;
    }

    public String getName() {
        return _start.getName().asString();
    }

    /**
     * @return the attribute with this name or null if no such attribute exists.
     */
    public AttributeToken getAttribute(String name) {
        return attributes().stream()
            .filter(attr -> attr.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public List<AttributeToken> attributes() {
        return _start.attributes();
    }

    public List<XMLElement> children() {
        return  _nodes.stream()
            .map(XMLNode::getTag)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult result = _start.parse(chars, index);
        if (result.isInvalid()) {
            return result;
        }
        int nextIndex = result.index();

        if (_start.isOpen()) {
            _nodes = new InnerNodes();
            _whitespace = new WhitespaceToken();
            _close = new XMLCloseTag();

            result = _nodes.parse(chars, nextIndex);
            if (result.isInvalid()) return result;
            nextIndex = result.index();

            result = _whitespace.parse(chars, nextIndex);
            nextIndex = result.index();

            result = _close.parse(chars, nextIndex);
            if (result.isInvalid()) return result;
        }

        return result;
    }

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    @Override
    public String toString() {
        String postfix = "";
        if (_start.isOpen()) {
            postfix = _nodes.toString() + _whitespace + _close;
        }

        return _start + postfix;
    }

    /**
     * @return a copy of the current node and all of its containing children.
     */
    @Override
    public XMLElement deepCopy() {
        XMLElement copy = new XMLElement();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(XMLElement other) {
        _start.setData(other._start);
        if (other._start.isOpen()) {
            _nodes = new InnerNodes();
            _whitespace = new WhitespaceToken();
            _close = new XMLCloseTag();

            _nodes.setData(other._nodes);
            _whitespace.setData(other._whitespace);
            _close.setData(other._close);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLElement)) return false;
        XMLElement other = (XMLElement) obj;

        return Objects.equals(_start, other._start)
            && Objects.equals(_nodes, other._nodes)
            && Objects.equals(_whitespace, other._whitespace)
            && Objects.equals(_close, other._close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_start, _nodes, _whitespace, _close);
    }
}
