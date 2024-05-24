package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionalBreak extends ASTNode {
    private ASTNode condition;

    @Override
    public String toString() {
        return "ConditionalBreak{" +
                "condition=" + condition +
                '}';
    }
}
