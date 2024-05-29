package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
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

    @Override
    public Object interpret(Context context) {
        Object value;

        if (this.value != null) {
            value = this.value.interpret(context);
        } else {
            value = null;
        }

        context.put(name, value);

        return value;
    }
}

