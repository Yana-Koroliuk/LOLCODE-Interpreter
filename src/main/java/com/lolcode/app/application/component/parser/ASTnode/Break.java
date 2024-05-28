package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import com.lolcode.app.application.exception.BreakException;
import lombok.*;

@Getter
@Setter
public class Break extends ASTNode {

    public Break() {
        super(ParseType.CONDITIONAL_BREAK);
    }

    @Override
    public String toString() {
        return "Break{" +
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
