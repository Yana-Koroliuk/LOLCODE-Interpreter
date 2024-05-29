package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Assignment extends ASTNode {
    private String variable;
    private ASTNode value;

    public Assignment(String variable, ASTNode value) {
        super(ParseType.ASSIGNMENT);
        this.variable = variable;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "variable='" + variable + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object value = this.value.interpret(context);

        context.put(variable, value);
        context.put("IT", value);

        return value;
    }
}
