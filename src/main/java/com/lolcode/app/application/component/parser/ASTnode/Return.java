package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class Return extends ASTNode {
    private ASTNode value;

    public Return(ASTNode value) {
        super(ParseType.RETURN);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Return{" +
                "value=" + value +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        return value.interpret(context);
    }
}
