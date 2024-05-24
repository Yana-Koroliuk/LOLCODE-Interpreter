package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Literal extends ASTNode {
    private String valueType;
    private Object value;

    @Override
    public String toString() {
        return "Literal{" +
                "valueType='" + valueType + '\'' +
                ", value=" + value +
                '}';
    }
}
