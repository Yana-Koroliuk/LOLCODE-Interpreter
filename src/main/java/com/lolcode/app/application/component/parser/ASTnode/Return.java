package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Return extends ASTNode {
    private ASTNode value;

    @Override
    public String toString() {
        return "Return{" +
                "value=" + value +
                '}';
    }
}
