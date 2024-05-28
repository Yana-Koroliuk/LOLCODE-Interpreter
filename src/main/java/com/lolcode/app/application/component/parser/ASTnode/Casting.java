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

        Object castedValue;
        try {
            castedValue = switch (castTo) {
                case "NUMBR" -> ((Number) interpretedValue).intValue();
                case "NUMBAR" -> ((Number) interpretedValue).doubleValue();
                case "YARN" -> String.valueOf(interpretedValue);
                case "TROOF" -> (Boolean) interpretedValue;
                default -> throw new IllegalArgumentException("Can't cast to " + castTo);
            };
        }
        catch (ClassCastException e) {
            throw new RuntimeException("Can't cast '" + interpretedValue + "' to " + castTo + ".");
        }

        return castedValue;
    }
}

