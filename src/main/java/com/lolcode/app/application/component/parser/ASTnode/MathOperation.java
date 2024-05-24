package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathOperation extends ASTNode {
    private String operator;
    private List<ASTNode> operands;

    @Override
    public String toString() {
        return "MathOperation{" +
                "operator='" + operator + '\'' +
                ", operands=" + operands +
                '}';
    }
}
