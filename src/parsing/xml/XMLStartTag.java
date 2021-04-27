package parsing.xml;

import essentials.contract.NoImplementationException;
import essentials.functional.Accumulator;
import parsing.model.*;

import java.util.List;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: TagHeader XMLTail
 */
public final class XMLStartTag extends AbstractXMLTag<XMLStartTag> /*, XMLStreamable*/  {

    protected XMLStartTag() {
        super(
            new CharTerminal('<'),
            new EitherNode<>(new CharTerminal('>'), new StringTerminal("/>"))
        );
    }

    public List<AttributeToken> attributes() {
        throw new NoImplementationException("TODO!");
    }

    public boolean isOpen() {
        return ((EitherNode<?, ?>) _postfix).hasFirst();
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
