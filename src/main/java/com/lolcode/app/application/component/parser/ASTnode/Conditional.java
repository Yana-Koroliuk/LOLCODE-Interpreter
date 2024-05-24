package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conditional extends ASTNode {
    private ASTNode condition;
    private Block trueBranch;
    private List<MebbeBranch> mebbeBranches;
    private Block falseBranch;

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
