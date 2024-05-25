package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class IterativeLoop extends ASTNode {
    private String label;
    private String operation;
    private ASTNode variable;
    private ASTNode condition;
    private List<ASTNode> body;

    public IterativeLoop(String label, String operation, ASTNode variable, ASTNode condition, List<ASTNode> body) {
        super(ParseType.ITERATIVE_LOOP);
        this.label = label;
        this.operation = operation;
        this.variable = variable;
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "IterativeLoop{" +
                "label='" + label + '\'' +
                ", operation='" + operation + '\'' +
                ", variable=" + variable +
                ", condition=" + condition +
                ", body=" + body +
                '}';
    }
}
