package parsing.xml;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * TODO: Simplified access for attribute/tag parsing only.
 * TODO: Have an enum as return parameter which controls the flow of the traversion (terminate, stop descent, continue)
 */
public interface XMLVisitor {

    void visitAttribute(AttributeToken attributeToken, XMLElement tag);

    void visitTag(XMLElement tag);

    static XMLVisitor of (@NotNull BiConsumer<AttributeToken, XMLElement> attributeConsumer,
                          @NotNull Consumer<XMLElement> tagConsumer
    ) {
        Contract.checkNulls(attributeConsumer, tagConsumer);

        return new XMLVisitor() {
            @Override
            public void visitAttribute(AttributeToken attributeToken, XMLElement tag) {
                attributeConsumer.accept(attributeToken, tag);
            }

            @Override
            public void visitTag(XMLElement tag) {
                tagConsumer.accept(tag);
            }
        };
    }
}
