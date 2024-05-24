package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDeclaration extends ASTNode {
    private String name;
    private List<String> params;
    private Block body;

    @Override
    public String toString() {
        return "FunctionDeclaration{" +
                "name='" + name + '\'' +
                ", params=" + params +
                ", body=" + body +
                '}';
    }
}
