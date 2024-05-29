package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import com.lolcode.app.application.exception.MebbeNotExecutedException;
import lombok.*;

@Getter
@Setter
public class MebbeBranch extends ASTNode {
    private ASTNode condition;
    private Block body;

    public MebbeBranch(ASTNode condition, Block body) {
        super(ParseType.MEBBE_BRANCH);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "MebbeBranch{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object result = condition.interpret(context);
        Boolean conditionValue;
        if (result instanceof Boolean) {
            conditionValue = (Boolean) result;
        } else {
            throw new IllegalArgumentException("The value in 'IT' should be a boolean");
        }
        if (conditionValue) {
            return body.interpret(context);
        } else throw new MebbeNotExecutedException();
    }
}
