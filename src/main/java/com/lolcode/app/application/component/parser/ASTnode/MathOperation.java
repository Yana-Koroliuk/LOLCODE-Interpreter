package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class MathOperation extends ASTNode {
    private String operator;
    private List<ASTNode> operands;

    public MathOperation(String operator, List<ASTNode> operands) {
        super(ParseType.MATH_OPERATION);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public String toString() {
        return "MathOperation{" +
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
            if (!(interpreted instanceof Number)) {
                throw new RuntimeException("Invalid operand for mathematical operation: " + interpreted);
            }
        }

        Double result;

        switch (operator) {
            case "SUM OF" -> {
                result = ((Number)interpretedOperands.get(0)).doubleValue() +
                        ((Number)interpretedOperands.get(1)).doubleValue();
            }
            case "DIFF OF" -> {
                result = ((Number)interpretedOperands.get(0)).doubleValue() -
                        ((Number)interpretedOperands.get(1)).doubleValue();
            }
            case "PRODUKT OF" -> {
                result = ((Number)interpretedOperands.get(0)).doubleValue() *
                        ((Number)interpretedOperands.get(1)).doubleValue();
            }
            case "QUOSHUNT OF" -> {
                if (((Number)interpretedOperands.get(1)).doubleValue() == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                result = ((Number)interpretedOperands.get(0)).doubleValue() /
                        ((Number)interpretedOperands.get(1)).doubleValue();
            }
            case "MOD OF" -> {
                if (((Number)interpretedOperands.get(1)).doubleValue() == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                result = ((Number)interpretedOperands.get(0)).doubleValue() %
                        ((Number)interpretedOperands.get(1)).doubleValue();
            }
            case "BIGGR OF" -> {
                result = Math.max(((Number)interpretedOperands.get(0)).doubleValue(),
                        ((Number)interpretedOperands.get(1)).doubleValue());
            }
            case "SMALLR OF" -> {
                result = Math.min(((Number)interpretedOperands.get(0)).doubleValue(),
                        ((Number)interpretedOperands.get(1)).doubleValue());
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        context.put("IT", result);
        return result;
    }
}
