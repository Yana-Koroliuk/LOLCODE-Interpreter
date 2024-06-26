package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;
import java.util.List;

@Getter
@Setter
public class Program extends ASTNode {
    private List<ASTNode> body;

    public Program() {
    }

    public Program(List<ASTNode> body) {
        super(ParseType.PROGRAM);
        this.body = body;
    }

    @Override
    public String toString() {
        return "Program{" +
                "body=" + body +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        Object result = null;

        for (ASTNode node : body) {
            result = node.interpret(context);
        }

        return result;
    }
}

