package parsing.xml;

import org.jetbrains.annotations.NotNull;
import parsing.model.*;

import java.util.Arrays;


/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: '</' ElementNameToken WhitespaceToken '>'
 */
public final class XMLCloseTag extends SequenceNode implements CopyNode<XMLCloseTag> {
    protected final StringTerminal _prefix;
    protected final WhitespaceToken _leadingWS;
    protected final ElementNameToken _name;
    protected final WhitespaceToken _trailingWS;
    protected final AbstractParseNode _postfix;

    protected XMLCloseTag() {
        _prefix = new StringTerminal("</");
        _leadingWS = new WhitespaceToken();
        _name = new ElementNameToken();
        _trailingWS = new WhitespaceToken();
        _postfix = new CharTerminal('>');

        _sequence.addAll(Arrays.asList(_prefix, _leadingWS, _name, _trailingWS, _postfix));
    }

    @Override
    public void setData(@NotNull XMLCloseTag other) {
        _leadingWS.setData(other._leadingWS);
        _name.setData(other._name);
        _trailingWS.setData(other._trailingWS);
    }

    @Override
    public XMLCloseTag deepCopy() {
        XMLCloseTag copy = new XMLCloseTag();
        copy.setData(this);

        return copy;
    }
}
