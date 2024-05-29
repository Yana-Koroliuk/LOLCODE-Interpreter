package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
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

    @Override
    public Object interpret(Context context) {
        Object conditionValue = condition.interpret(context);
        Object caseResult = null;

        Integer firstTrueCaseIndex = null;

        for (int i = 0; i < cases.size(); i++) {
            if (cases.get(i).getValue().interpret(context).equals(conditionValue)) {
                firstTrueCaseIndex = i;
                break;
            }
        }

        if (firstTrueCaseIndex != null) {
            for (int i = firstTrueCaseIndex; i < cases.size(); i++) {
                caseResult = cases.get(i).interpret(context);
                if (caseResult == Break.BREAK) break;
            }
        } else {
            if (defaultCase != null) {
                return defaultCase.interpret(context);
            }
        }
        return caseResult;
    }
}
