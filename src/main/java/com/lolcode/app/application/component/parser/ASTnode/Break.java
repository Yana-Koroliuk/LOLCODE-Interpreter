package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Break extends ASTNode {

    public static final Object BREAK = new Object();

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
        return BREAK;
    }
}
