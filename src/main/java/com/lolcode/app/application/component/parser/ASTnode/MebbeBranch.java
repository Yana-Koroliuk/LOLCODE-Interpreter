package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MebbeBranch extends ASTNode {
    private ASTNode condition;
    private Block body;

    @Override
    public String toString() {
        return "MebbeBranch{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
