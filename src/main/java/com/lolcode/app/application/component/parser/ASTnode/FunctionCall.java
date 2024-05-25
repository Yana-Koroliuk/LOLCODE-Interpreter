package com.lolcode.app.application.component.parser.ASTnode;

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
}
