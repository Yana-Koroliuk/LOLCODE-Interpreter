package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Case extends ASTNode {
    private ASTNode value;
    private Block body;

    @Override
    public String toString() {
        return "Case{" +
                "value=" + value +
                ", body=" + body +
                '}';
    }
}
