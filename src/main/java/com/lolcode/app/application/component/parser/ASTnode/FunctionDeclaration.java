package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class FunctionDeclaration extends ASTNode {
    private String name;
    private List<String> params;
    private List<ASTNode> body;

    public FunctionDeclaration(String name, List<String> params, List<ASTNode> body) {
        super(ParseType.FunctionDeclaration);
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FunctionDeclaration{" +
                "name='" + name + '\'' +
                ", params=" + params +
                ", body=" + body +
                '}';
    }
}
