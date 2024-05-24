package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Block extends ASTNode {
    private List<ASTNode> body;

    @Override
    public String toString() {
        return "Block{" +
                "body=" + body +
                '}';
    }
}
