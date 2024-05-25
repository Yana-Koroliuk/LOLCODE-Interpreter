package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Loop extends ASTNode {
    private String label;
    private String operation;
    private ASTNode variable;
    private ASTNode condition;
    private Block body;

    public Loop(String label, Block body) {
        super(ParseType.LOOP);
        this.label = label;
        this.body = body;
    }

    public Loop(String label, String operation, ASTNode variable, ASTNode condition, Block body) {
        super(ParseType.LOOP);
        this.label = label;
        this.operation = operation;
        this.variable = variable;
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Loop{" +
                "label='" + label + '\'' +
                ", operation='" + operation + '\'' +
                ", variable=" + variable +
                ", condition=" + condition +
                ", body=" + body +
                '}';
    }
}
