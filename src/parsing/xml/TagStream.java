package parsing.xml;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 */
public interface TagStream extends XMLStream<XMLElement, TagStream>{

    AttributeStream findAttributes();

    AttributeStream findAttributes(String attributeName);

    AttributeStream findAttributes(Predicate<AttributeToken> predicate);

    OptionalTag findFirst();

    <T> Stream<T> map(Function<XMLElement, T> mapper);

    <T> Stream<T> flatMap(Function<XMLElement, Stream<T>> mapper);

    Stream<XMLElement> stream();

}
