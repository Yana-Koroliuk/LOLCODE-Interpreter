package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import com.lolcode.app.application.exception.BreakException;
import lombok.*;

@Getter
@Setter
public class ConditionalBreak extends ASTNode {
    private ASTNode condition;

    public ConditionalBreak(ASTNode condition) {
        super(ParseType.CONDITIONAL_BREAK);
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ConditionalBreak{" +
                "condition=" + condition +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object condition =  this.condition.interpret(context);
        if (!(condition instanceof Boolean) || (Boolean) condition) {
            throw new BreakException();
        }
        return null;
    }
}
