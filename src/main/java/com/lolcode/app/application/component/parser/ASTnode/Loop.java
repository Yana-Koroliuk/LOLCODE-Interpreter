package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loop extends ASTNode {
    private String label;
    private Block body;

    @Override
    public String toString() {
        return "Loop{" +
                "label='" + label + '\'' +
                ", body=" + body +
                '}';
    }
}
