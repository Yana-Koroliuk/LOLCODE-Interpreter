package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Program extends ASTNode {
    private List<ASTNode> body;

    @Override
    public String toString() {
        return "Program{" +
                "body=" + body +
                '}';
    }
}

