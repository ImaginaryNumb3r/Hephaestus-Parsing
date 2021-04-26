package parsing.model;

import java.util.function.Supplier;

/**
 * @author Patrick Plieschnegger
 */
public class OneOrMoreNode<T extends CopyNode<T>> extends MultiNode<T> {

    public OneOrMoreNode(Supplier<T> tokenConstructor) {
        super(tokenConstructor);
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        T mandatoryToken = _tokenConstructor.get();

        var result = mandatoryToken.parse(chars, index);
        if (result.isValid()) {
            _elements.add(mandatoryToken);

            result = super.parseImpl(chars, result.index());
        }

        return result;
    }
}
