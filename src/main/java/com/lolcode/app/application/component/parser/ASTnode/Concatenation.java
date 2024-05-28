package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Concatenation extends ASTNode {
    private List<ASTNode> values;

    public Concatenation(List<ASTNode> values) {
        super(ParseType.CONCATENATION);
        this.values = values;
    }

    @Override
    public String toString() {
        return "Concatenation{" +
                "values=" + values +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        StringBuilder sb = new StringBuilder();
        for (ASTNode node : values) {
            sb.append(node.interpret(context).toString());
        }
        context.put("IT", sb.toString());
        return sb.toString();
    }
}
