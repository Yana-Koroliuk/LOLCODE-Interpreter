package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class ConditionalBreak extends ASTNode {
    private ASTNode condition;

    public ConditionalBreak(ASTNode condition) {
        super(ParseType.ConditionalBreak);
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ConditionalBreak{" +
                "condition=" + condition +
                '}';
    }
}
