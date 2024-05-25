package com.lolcode.app.application.component.parser.ASTnode;

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
}
