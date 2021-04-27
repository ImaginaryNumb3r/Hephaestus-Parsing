package parsing.xml;

import parsing.model.CharTerminal;
import parsing.model.EitherNode;
import parsing.model.StringTerminal;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: TagHeader XMLTail
 */
public final class XMLCloseTag extends AbstractXMLTag<XMLCloseTag> /*, XMLStreamable*/  {

    protected XMLCloseTag() {
        super(
            new StringTerminal("</"),
            new CharTerminal('>')
        );
    }

    @Override
    public void setData(XMLCloseTag other) {
        super.setData(other);
    }

    @Override
    public XMLCloseTag deepCopy() {
        XMLCloseTag copy = new XMLCloseTag();
        copy.setData(this);

        return copy;
    }
}
