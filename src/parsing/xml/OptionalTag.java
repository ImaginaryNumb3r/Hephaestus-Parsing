package parsing.xml;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Patrick Plieschnegger
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalTag implements OptionalXML<XMLElement> {
    private final Optional<XMLElement> _tag;

    public OptionalTag(Optional<XMLElement> attribute) {_tag = attribute;}

    public Optional<XMLElement> get() {
        return _tag;
    }

    public Optional<String> getName() {
        return _tag.map(XMLElement::getName);
    }

    public Optional<AttributeToken> getAttributeToken(String attributeName) {
        return _tag.map(tag -> tag.getAttribute(attributeName));
    }

    public <T> Optional<T> map(Function<XMLElement, T> mapper) {
        return _tag.map(mapper);
    }

    public Optional<String> getAttribute(String keyName) {
        return getAttributeToken(keyName).map(AttributeToken::getValue);
    }

}
