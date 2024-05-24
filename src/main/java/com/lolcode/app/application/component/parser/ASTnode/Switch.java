package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Switch extends ASTNode {
    private ASTNode condition;
    private List<Case> cases;
    private DefaultCase defaultCase;

    @Override
    public String toString() {
        return "Switch{" +
                "condition=" + condition +
                ", cases=" + cases +
                ", defaultCase=" + defaultCase +
                '}';
    }
}
