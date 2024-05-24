package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariableDeclaration extends ASTNode {
    private String name;
    private ASTNode value;

    @Override
    public String toString() {
        return "VariableDeclaration{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

