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
                    return Integer.parseInt(interpretedValue.toString());
                }
                case "NUMBAR" -> {
                    return Double.parseDouble(interpretedValue.toString());
                }
                case "YARN" -> {
                    return String.valueOf(interpretedValue);
                }
                case "TROOF" -> {
                    if (interpretedValue.equals("WIN")) return true;
                    else if (interpretedValue.equals("FAIL")) return false;
                }
                case "NOOB" -> {
                    return null;
                }
                default -> throw new IllegalArgumentException("Can't cast to " + castTo);
            }
        }
        catch (ClassCastException e) {
            throw new RuntimeException("Can't cast '" + interpretedValue + "' to " + castTo + ".");
        }
        return null;
    }
}

