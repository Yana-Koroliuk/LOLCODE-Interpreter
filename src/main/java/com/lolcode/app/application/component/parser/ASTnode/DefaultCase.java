package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class DefaultCase extends ASTNode {
    private Block body;

    public DefaultCase(Block body) {
        super(ParseType.DefaultCase);
        this.body = body;
    }

    @Override
    public String toString() {
        return "DefaultCase{" +
                "body=" + body +
                '}';
    }
}
