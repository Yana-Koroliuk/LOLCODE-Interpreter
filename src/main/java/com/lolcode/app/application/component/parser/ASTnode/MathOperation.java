package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class MathOperation extends ASTNode {
    private String operator;
    private List<ASTNode> operands;

    public MathOperation(String operator, List<ASTNode> operands) {
        super(ParseType.MathOperation);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public String toString() {
        return "MathOperation{" +
                "operator='" + operator + '\'' +
                ", operands=" + operands +
                '}';
    }
}
