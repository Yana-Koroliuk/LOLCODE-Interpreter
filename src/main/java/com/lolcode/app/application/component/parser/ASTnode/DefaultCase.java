package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class DefaultCase extends ASTNode {
    private Block body;

    public DefaultCase(Block body) {
        super(ParseType.DEFAULT_CASE);
        this.body = body;
    }

    @Override
    public String toString() {
        return "DefaultCase{" +
                "body=" + body +
                '}';
    }
}
