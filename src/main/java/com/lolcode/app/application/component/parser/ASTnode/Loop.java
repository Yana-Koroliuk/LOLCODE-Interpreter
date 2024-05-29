package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;


@Getter
@Setter
public class Loop extends ASTNode {
    private String label;
    private String operation;
    private ASTNode variable;
    private ASTNode condition;
    private Block body;

    public Loop(String label, Block body) {
        super(ParseType.LOOP);
        this.label = label;
        this.body = body;
    }

    public Loop(String label, String operation, ASTNode variable, ASTNode condition, Block body) {
        super(ParseType.LOOP);
        this.label = label;
        this.operation = operation;
        this.variable = variable;
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Loop{" +
                "label='" + label + '\'' +
                ", operation='" + operation + '\'' +
                ", variable=" + variable +
                ", condition=" + condition +
                ", body=" + body +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object result;

        if(operation == null && variable == null && condition == null) {
            while (true) {
                result = body.interpret(context);
                if (result == Break.BREAK) break;
            }
            return result;
        }

        Object variableValue;
        Number value;
        assert variable != null;
        String varName = ((Identifier) variable).getName();
        try {
            variableValue = variable.interpret(context);
            if (variableValue instanceof Number) {
                value = (Number) variableValue;
            } else {
                throw new IllegalArgumentException("Variable value is not a number");
            }
        } catch (IllegalArgumentException e) {
            if (variable instanceof Identifier) {
                context.put(varName, 0);
                value = 0;
            } else throw e;
        }

        switch (operation) {
            case "UPPIN" -> {
                while (!(boolean) condition.interpret(context)) {
                    result = body.interpret(context);
                    if (result == Break.BREAK) break;
                    context.put(varName, value = (int) value + 1);
                }
            }
            case "NERFIN" -> {
                while (!(boolean) condition.interpret(context)) {
                    result = body.interpret(context);
                    if (result == Break.BREAK) break;
                    context.put(varName, value = (int) value - 1);
                }
            }
            default -> throw new IllegalArgumentException("Unknown loop operation: " + operation);
        }
        return null;
    }
}
