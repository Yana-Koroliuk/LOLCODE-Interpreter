package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class FunctionCall extends ASTNode {
    private String name;
    private List<ASTNode> args;

    public FunctionCall(String name, List<ASTNode> args) {
        super(ParseType.FUNCTION_CALL);
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "name='" + name + '\'' +
                ", args=" + args +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        if(!context.containsKey(name)) {
            throw new IllegalStateException("Function '" + name + "' is not declared.");
        }

        if (!(context.get(name) instanceof FunctionDeclaration function)) {
            throw new IllegalStateException("'" + name + "' is not a function.");
        }

        Context newContext = new Context();
        newContext.putAll(context);

        if (function.getParams().size() != args.size()) {
            throw new IllegalArgumentException("Argument mismatch for function: " +  name);
        }

        for (int i = 0; i < args.size(); i++) {
            newContext.put(function.getParams().get(i), args.get(i).interpret(context));
        }

        return function.getBody().interpret(newContext);
    }
}
