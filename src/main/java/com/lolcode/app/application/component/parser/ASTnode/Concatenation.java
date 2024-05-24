package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concatenation extends ASTNode {
    private List<ASTNode> values;

    @Override
    public String toString() {
        return "Concatenation{" +
                "values=" + values +
                '}';
    }
}
