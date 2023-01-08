package parsing.model.token;

import parsing.model.basic.CopyNode;
import parsing.model.node.ConsumerNode;

public final class IntegerToken extends ConsumerNode implements CopyNode<IntegerToken> {
    public IntegerToken() {
        super(Character::isDigit);
    }

    @Override
    public IntegerToken deepCopy() {
        return CopyNode.deepCopy(this, IntegerToken::new);
    }

    @Override
    public void setData(IntegerToken other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
