package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Identifier extends ASTNode {
    private String name;

    public Identifier(String name) {
        super(ParseType.IDENTIFIER);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "name='" + name + '\'' +
                '}';
    }
}
