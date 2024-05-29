package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Print extends ASTNode {
    private ASTNode value;

    public Print(ASTNode value) {
        super(ParseType.PRINT);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Print{" +
                "value=" + value +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        String valueToPrint = value.interpret(context).toString();
        System.out.println(valueToPrint);
        context.put("IT", valueToPrint);
        return null;
    }
}
