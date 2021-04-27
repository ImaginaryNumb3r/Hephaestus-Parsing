package parsing.xml;

import essentials.contract.NoImplementationException;
import essentials.functional.Predicates;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ final class TagStreamImpl extends XMLStreamImpl<XMLElement, TagStream> implements TagStream {

    /*package*/ TagStreamImpl(@NotNull List<XMLElement> tags,
                              @Nullable XMLStreamImpl<XMLElement, TagStream> parent
    ) {
        super(tags, TagStreamImpl::new, parent);
    }

    /*package*/ TagStreamImpl(@NotNull List<XMLElement> tags) {
        this(tags, null);
    }

    @Override
    public TagStream filter(String name) {
        return filter(tag -> tag.getName().equals(name));
    }

    @Override
    public TagStream filter(Predicate<XMLElement> predicate) {
        // TODO: Make it that filter only sets the name for the tag that is searched.
        // The actual execution of the path is done in a separate method and executed on the terminal operations.
        // _parent.filter()
        throw new NoImplementationException();

        /*
        var filtered = new ArrayList<XMLElement>();

        for (XMLElement XMLElement : _values) {
            if (predicate.test(XMLElement)) {
                filtered.add(XMLElement);
            }
        }

        return new TagStreamImpl(filtered); */
    }

    @Override
    public AttributesStreamImpl findAttributes() {
        return findAttributes(Predicates::alwaysTrue);
    }

    @Override
    public AttributesStreamImpl findAttributes(String attributeName) {
        return findAttributes(attr -> attr.getName().equals(attributeName));
    }

    @Override
    public AttributesStreamImpl findAttributes(Predicate<AttributeToken> predicate) {
        var attributes = new ArrayList<AttributeToken>();

        for (XMLElement tag : _values) {
            for (AttributeToken attribute : tag.attributes()) {
                if (predicate.test(attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return new AttributesStreamImpl(attributes);
    }

    @Override
    public OptionalTag findFirst() {
        Optional<XMLElement> XMLElement = _values.isEmpty()
            ? Optional.empty()
            : Optional.of(_values.get(0));

        return new OptionalTag(XMLElement);
    }

    @Override
    public <T> Stream<T> map(Function<XMLElement, T> mapper) {
        return _values.stream().map(mapper);
    }

    @Override
    public <T> Stream<T> flatMap(Function<XMLElement, Stream<T>> mapper) {
        return _values.stream().flatMap(mapper);
    }

    @Override
    public Stream<XMLElement> stream() {
        return _values.stream();
    }
}
