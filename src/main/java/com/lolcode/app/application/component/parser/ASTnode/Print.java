package com.lolcode.app.application.component.parser.ASTnode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Print extends ASTNode {
    private ASTNode value;

    @Override
    public String toString() {
        return "Print{" +
                "value=" + value +
                '}';
    }
}
