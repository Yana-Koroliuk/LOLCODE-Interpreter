package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Input extends ASTNode {
    private final String name;

    public Input(String name) {
        super(ParseType.INPUT);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Input{" +
                "name='" + name + '\'' +
                '}';
    }
}
