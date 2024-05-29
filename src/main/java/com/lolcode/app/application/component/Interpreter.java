package com.lolcode.app.application.component;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.domain.SyntaxTree;

public class Interpreter {

    private Context context;

    public Interpreter() {
        this.context = new Context();
        this.context.put("IT", null);
    }

    public Object interpret(SyntaxTree syntaxTree) {
        return syntaxTree.getProgram().interpret(context);
    }
}
