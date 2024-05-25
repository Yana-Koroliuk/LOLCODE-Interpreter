package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
public class MebbeBranch extends ASTNode {
    private ASTNode condition;
    private Block body;

    public MebbeBranch(ASTNode condition, Block body) {
        super(ParseType.MEBBE_BRANCH);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "MebbeBranch{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
