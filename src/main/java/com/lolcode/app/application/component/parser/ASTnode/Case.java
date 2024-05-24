package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Case extends ASTNode {
    private ASTNode value;
    private List<ASTNode> body;

    public Case(ASTNode value, List<ASTNode> body) {
        super(ParseType.Case);
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
