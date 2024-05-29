package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Casting extends ASTNode {
    private ASTNode value;
    private String castTo;

    public Casting(ASTNode value, String castTo) {
        super(ParseType.CASTING);
        this.value = value;
        this.castTo = castTo;
    }

    @Override
    public String toString() {
        return "Casting{" +
                "value=" + value +
                ", castTo='" + castTo + '\'' +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object interpretedValue = this.value.interpret(context);

        try {
            switch (castTo) {
                case "NUMBR" -> {
                    int value = Integer.parseInt(interpretedValue.toString());
                    context.put("IT", value);
                    return value;
                }
                case "NUMBAR" -> {
                    double value = Double.parseDouble(interpretedValue.toString());
                    context.put("IT", value);
                    return value;
                }
                case "YARN" -> {
                    String value = String.valueOf(interpretedValue);
                    context.put("IT", value);
                    return value;
                }
                case "TROOF" -> {
                    Boolean value = null;
                    if (interpretedValue.equals("WIN")) value = true;
                    else if (interpretedValue.equals("FAIL")) value = false;
                    context.put("IT", value);
                    return value;
                }
                case "NOOB" -> {
                    context.put("IT", null);
                    return null;
                }
                default -> throw new IllegalArgumentException("Can't cast to " + castTo);
            }
        }
        catch (ClassCastException e) {
            throw new RuntimeException("Can't cast '" + interpretedValue + "' to " + castTo + ".");
        }
    }
}

