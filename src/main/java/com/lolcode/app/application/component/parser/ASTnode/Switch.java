package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Switch extends ASTNode {
    private ASTNode condition;
    private List<Case> cases;
    private DefaultCase defaultCase;

    public Switch(ASTNode condition, List<Case> cases, DefaultCase defaultCase) {
        super(ParseType.SWITCH);
        this.condition = condition;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "condition=" + condition +
                ", cases=" + cases +
                ", defaultCase=" + defaultCase +
                '}';
    }
}
