package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionStatement extends ASTNode {
    private ASTNode expression;

    @Override
    public String toString() {
        return "ExpressionStatement{" +
                "expression=" + expression +
                '}';
    }
}
