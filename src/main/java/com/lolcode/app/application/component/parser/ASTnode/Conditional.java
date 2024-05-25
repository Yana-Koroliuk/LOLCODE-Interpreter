package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Conditional extends ASTNode {
    private ASTNode condition;
    private Block trueBranch;
    private List<MebbeBranch> mebbeBranches;
    private Block falseBranch;

    public Conditional(ASTNode condition, Block trueBranch, List<MebbeBranch> mebbeBranches, Block falseBranch) {
        super(ParseType.CONDITIONAL);
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.mebbeBranches = mebbeBranches;
        this.falseBranch = falseBranch;
    }

    @Override
    public String toString() {
        return "Conditional{" +
                "condition=" + condition +
                ", trueBranch=" + trueBranch +
                ", mebbeBranches=" + mebbeBranches +
                ", falseBranch=" + falseBranch +
                '}';
    }
}
