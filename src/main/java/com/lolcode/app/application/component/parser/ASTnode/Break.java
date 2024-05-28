package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Break extends ASTNode {

    public Break() {
        super(ParseType.CONDITIONAL_BREAK);
    }

    @Override
    public String toString() {
        return "Break{" +
                '}';
    }
}
