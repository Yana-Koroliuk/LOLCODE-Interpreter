package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment extends ASTNode {
    private String variable;
    private ASTNode value;

    @Override
    public String toString() {
        return "Assignment{" +
                "variable='" + variable + '\'' +
                ", value=" + value +
                '}';
    }
}
