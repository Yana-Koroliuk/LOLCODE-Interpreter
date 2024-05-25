package com.lolcode.app.application.component.parser.ASTnode;

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
}

