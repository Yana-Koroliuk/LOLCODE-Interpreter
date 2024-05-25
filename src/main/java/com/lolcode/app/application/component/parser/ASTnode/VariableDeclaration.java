package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class VariableDeclaration extends ASTNode {
    private String name;
    private ASTNode value;

    public VariableDeclaration(String name, ASTNode value) {
        super(ParseType.VARIABLE_DECLARATION);
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "VariableDeclaration{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

