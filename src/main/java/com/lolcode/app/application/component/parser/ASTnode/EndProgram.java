package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class EndProgram extends ASTNode {

    public EndProgram() {
        super(ParseType.END_PROGRAM);
    }

    @Override
    public String toString() {
        return "EndProgram{}";
    }

    @Override
    public Object interpret(Context context) {
        System.exit(0);
        return null;
    }
}

