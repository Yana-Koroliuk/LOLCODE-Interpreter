package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IterativeLoop extends ASTNode {
    private String label;
    private String operation;
    private ASTNode variable;
    private ASTNode condition;
    private Block body;

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
