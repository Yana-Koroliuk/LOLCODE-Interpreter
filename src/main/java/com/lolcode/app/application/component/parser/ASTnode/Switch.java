package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import com.lolcode.app.application.exception.BreakException;
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
        boolean caseExecuted = false;

        try {
            for (Case caseObj : cases) {
                if (caseObj.getValue().interpret(context).equals(conditionValue)) {
                    caseResult = caseObj.interpret(context);
                    caseExecuted = true;
                }
            }
        } catch (BreakException e) {
            return caseResult;
        }
        if (defaultCase != null && !caseExecuted) {
            return defaultCase.interpret(context);
        }
        return caseResult;
    }
}
