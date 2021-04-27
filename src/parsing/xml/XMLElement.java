package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseNode;
import parsing.model.ParseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 *  - XMLElement:
 *      - XmlTag1 Nodes XmlTag2: where XmlTag1 is open and XmlTag2 is closing
 *      - XmlTag: where XmlTag is closed
 */
public class XMLElement extends AbstractParseNode implements CopyNode<XMLElement> {
    private final XMLStartTag _start;
    private InnerNodes _nodes; // optional
    private XMLCloseTag _close; // optional <- we only need this for parsing, we can get rid of it afterwards

    public XMLElement() {
        _start = new XMLStartTag();
        _nodes = null;
        _close = null;
    }

    public String getName() {
        return _start._name.asString();
    }

    /**
     * Only returns the first attribute with this name.
     * @param name
     * @return
     */
    public AttributeToken getAttribute(String name) {
        throw new NoImplementationException("TODO!");
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
            _close = new XMLCloseTag();

            result = _nodes.parse(chars, nextIndex);
            if (result.isInvalid()) return result;
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
            postfix = _nodes.toString() + _close.toString();
        }

        return _start + postfix;
    }

    /**
     * @return a copy of the current node and all of its containing children.
     */
    @Override
    public XMLElement deepCopy() {
        throw new NoImplementationException();
    }

    @Override
    public void setData(XMLElement other) {
        _start.setData(other._start);
        if (_start.isOpen()) {
            _nodes.setData(other._nodes);
            _close.setData(other._close);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLElement)) return false;
        XMLElement other = (XMLElement) obj;

        return Objects.equals(_start, other._start)
            && Objects.equals(_nodes, other._nodes)
            && Objects.equals(_close, other._close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_start, _nodes, _close);
    }
}
