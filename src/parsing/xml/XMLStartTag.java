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
    private final ElementNameToken _name;
    private final WhitespaceToken _whitespace;
    private final OptionalNode<XMLAttributes> _attributes;
    private final EitherNode<CharTerminal, StringTerminal> _postfix;

    protected XMLStartTag() {
        _prefix = new CharTerminal('<');
        _name = new ElementNameToken();
        _whitespace = new WhitespaceToken();
        _attributes = new OptionalNode<>(new XMLAttributes());
        _postfix = new EitherNode<>(new CharTerminal('>'), new StringTerminal("/>"));

        _sequence.addAll(Arrays.asList(_prefix, _name, _attributes, _whitespace, _postfix));
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
    }

    @Override
    public XMLStartTag deepCopy() {
        XMLStartTag copy = new XMLStartTag();
        copy.setData(this);

        return copy;
    }
}
