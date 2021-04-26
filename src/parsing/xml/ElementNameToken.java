package parsing.xml;

import parsing.model.ConsumerNode;
import parsing.model.CopyNode;

import java.util.Set;

/**
 * @author Patrick Plieschnegger
 */
/*protected*/ class ElementNameToken extends ConsumerNode implements CopyNode<ElementNameToken> {
    private static final Set<Character> ALLOWED_CHARS = Set.of('_', '.', ':', '-');

    public ElementNameToken() {
        super(ch -> Character.isAlphabetic(ch) || Character.isDigit(ch) || ALLOWED_CHARS.contains(ch));
    }

    public void setName(CharSequence name) {
        _buffer.setLength(0);
        _buffer.append(name);
    }

    @Override
    public void setData(ElementNameToken other) {
        setName(other._buffer);
    }

    @Override
    public ElementNameToken deepCopy() {
        ElementNameToken copy = new ElementNameToken();
        copy.setData(this);

        return copy;
    }
}
