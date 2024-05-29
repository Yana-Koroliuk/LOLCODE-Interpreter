package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class BooleanOperation extends ASTNode {
    private String operator;
    private List<ASTNode> operands;

    public BooleanOperation(String operator, List<ASTNode> operands) {
        super(ParseType.BOOLEAN_OPERATION);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public String toString() {
        return "BooleanOperation{" +
                "operator='" + operator + '\'' +
                ", operands=" + operands +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        List<Object> interpretedOperands = operands.stream()
                .map(operand -> operand.interpret(context))
                .toList();

        for (Object interpreted : interpretedOperands) {
            if (!(interpreted instanceof Boolean) && !(interpreted instanceof Number)) {
                throw new RuntimeException("Invalid operand for boolean operation: " + interpreted);
            }
        }

        Boolean result;

        switch (operator) {
            case "BOTH OF" -> {
                result = ((Boolean) interpretedOperands.get(0)) &&
                        ((Boolean) interpretedOperands.get(1));
            }
            case "EITHER OF" -> {
                result = ((Boolean) interpretedOperands.get(0)) ||
                        ((Boolean) interpretedOperands.get(1));
            }
            case "WON OF" -> {
                result = ((Boolean) interpretedOperands.get(0)) ^
                        ((Boolean) interpretedOperands.get(1));
            }
            case "NOT" -> {
                result = !((Boolean) interpretedOperands.get(0));
            }
            case "ALL OF" -> {
                boolean result1 = true;
                for (Object operand : interpretedOperands) {
                    result1 &= (Boolean) operand;
                }
                result = result1;
            }
            case "ANY OF" -> {
                boolean result1 = false;
                for (Object operand : interpretedOperands) {
                    result1 |= (Boolean) operand;
                }
                result = result1;
            }
            case "BOTH SAEM" -> {
                result = ((Number) interpretedOperands.get(0)).doubleValue() ==
                        ((Number) interpretedOperands.get(1)).doubleValue();
            }
            case "DIFFRINT" -> {
                result = ((Number) interpretedOperands.get(0)).doubleValue() !=
                        ((Number) interpretedOperands.get(1)).doubleValue();
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        context.put("IT", result);
        return result;
    }
}

