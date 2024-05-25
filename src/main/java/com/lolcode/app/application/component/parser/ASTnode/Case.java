package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Case extends ASTNode {
    private ASTNode value;
    private Block body;

    public Case(ASTNode value, Block body) {
        super(ParseType.CASE);
        this.value = value;
        this.body = body;
    }
    @Override
    public String toString() {
        return "Case{" +
                "value=" + value +
                ", body=" + body +
                '}';
    }
}
