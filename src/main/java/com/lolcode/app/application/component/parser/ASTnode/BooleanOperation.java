package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class BooleanOperation extends ASTNode {
    private String operator;
    private List<ASTNode> operands;

    public BooleanOperation(String operator, List<ASTNode> operands) {
        super(ParseType.BOOLEAN_OPERATION);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public String toString() {
        return "BooleanOperation{" +
                "operator='" + operator + '\'' +
                ", operands=" + operands +
                '}';
    }

}

