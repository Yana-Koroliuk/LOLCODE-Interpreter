package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.interpreter.Interpretable;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ASTNode implements Interpretable {
    private ParseType type;

    @Override
    public abstract Object interpret(Context context);
}
