package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Literal extends ASTNode {
    private String valueType;
    private Object value;

    public Literal(String valueType, Object value) {
        super(ParseType.LITERAL);
        this.valueType = valueType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "valueType='" + valueType + '\'' +
                ", value=" + value +
                '}';
    }
}
