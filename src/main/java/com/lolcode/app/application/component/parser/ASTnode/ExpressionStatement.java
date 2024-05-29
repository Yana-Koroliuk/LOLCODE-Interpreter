package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class ExpressionStatement extends ASTNode {
    private ASTNode expression;

    public ExpressionStatement(ASTNode expression) {
        super(ParseType.EXPRESSION_STATEMENT);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ExpressionStatement{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object value = expression.interpret(context);
        context.put("IT", value);
        return value;
    }
}
