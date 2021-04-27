package parsing.xml;

import org.jetbrains.annotations.NotNull;
import parsing.model.*;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: TagHeader XMLTail
 * - XMLTag 1: '<' ElementNameToken (Spacetoken XmlAttributes)? Whitespacetoken '>' | '/>'
 * - XMLTag 2: '</' ElementNameToken Whitespacetoken '>'
 */
/*package*/ abstract class AbstractXMLTag<T extends AbstractXMLTag<T>> extends SequenceNode implements CopyNode<T> /*, XMLStreamable*/  {
    protected final AbstractParseNode _prefix;
    protected final ElementNameToken _name;
    protected final WhitespaceToken _whitespace;
    protected final AbstractParseNode _postfix;

    protected AbstractXMLTag(AbstractParseNode prefix, AbstractParseNode postfix) {
        _prefix = prefix;
        _name = new ElementNameToken();
        _whitespace = new WhitespaceToken();
        _postfix = postfix;

        _sequence.addAll(Arrays.asList(_prefix, _name, _whitespace, _postfix));
    }

    public void setData(T other) {
        super.setData(other);
    }

    @Override
    public abstract T deepCopy();
}
