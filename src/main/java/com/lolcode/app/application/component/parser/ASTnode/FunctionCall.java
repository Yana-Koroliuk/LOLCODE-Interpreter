package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionCall extends ASTNode {
    private String name;
    private List<ASTNode> args;

    @Override
    public String toString() {
        return "FunctionCall{" +
                "name='" + name + '\'' +
                ", args=" + args +
                '}';
    }
}
