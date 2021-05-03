package parsing.xml;

import parsing.model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: '<' ElementNameToken (Spacetoken XmlAttributes)? Whitespacetoken ('>' { isOpen=true } | '/>' )
 */
public final class XMLStartTag extends SequenceNode implements CopyNode<XMLStartTag> /*, XMLStreamable*/  {
    private final CharTerminal _prefix;
    private ElementNameToken _name;
    private OptionalNode<XMLAttributes> _attributes;
    private WhitespaceToken _whitespace;
    private EitherNode<CharTerminal, StringTerminal> _postfix;

    protected XMLStartTag() {
        this(
            new CharTerminal('<'),
            new ElementNameToken(),
            new OptionalNode<>(new XMLAttributes()),
            new WhitespaceToken(),
            new EitherNode<>(new CharTerminal('>'), new StringTerminal("/>"))
        );
    }

    public XMLStartTag(CharTerminal prefix, ElementNameToken name, OptionalNode<XMLAttributes> attributes, WhitespaceToken whitespace, EitherNode<CharTerminal, StringTerminal> postfix) {
        super(Arrays.asList(prefix, name, attributes, whitespace, postfix));
        _prefix = prefix;
        _name = name;
        _whitespace = whitespace;
        _attributes = attributes;
        _postfix = postfix;
    }

    public ElementNameToken getName() {
        return _name;
    }

    public WhitespaceToken getWhitespace() {
        return _whitespace;
    }

    public List<AttributeToken> attributes() {
        return  _attributes.fetch().stream()
            .map(XMLAttributes::attributes)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public boolean isOpen() {
        return _postfix.hasFirst();
    }

    @Override
    public void setData(XMLStartTag other) {
        super.setData(other);
        _name = other._name;
        _whitespace = other._whitespace;
        _attributes = other._attributes;
        _postfix = other._postfix;
    }

    @Override
    public XMLStartTag deepCopy() {
        return new XMLStartTag(
            _prefix,
            _name,
            _attributes,
            _whitespace,
            _postfix
        );
    }
}
