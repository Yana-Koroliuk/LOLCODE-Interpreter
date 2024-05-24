package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Block extends ASTNode {
    private List<ASTNode> body;

    public Block(List<ASTNode> body) {
        super(ParseType.Block);
        this.body = body;
    }

    @Override
    public String toString() {
        return "Block{" +
                "body=" + body +
                '}';
    }
}
