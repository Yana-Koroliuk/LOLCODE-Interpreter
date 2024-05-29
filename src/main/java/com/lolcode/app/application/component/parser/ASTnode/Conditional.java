package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import com.lolcode.app.application.exception.MebbeNotExecutedException;
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

    @Override
    public Object interpret(Context context) {
        Object result = condition.interpret(context);
        Boolean conditionValue;
        if (result instanceof Boolean) {
            conditionValue = (Boolean) result;
        } else {
            throw new IllegalArgumentException("The value in 'IT' should be a boolean");
        }

        if (conditionValue) {
            return trueBranch.interpret(context);
        } else if (!mebbeBranches.isEmpty()) {
            try {
                for (MebbeBranch mebbeBranch : mebbeBranches) {
                    Object mebbeResult = mebbeBranch.interpret(context);
                    if (mebbeResult != null) {
                        return mebbeResult;
                    }
                }
            } catch (MebbeNotExecutedException e) {
                return falseBranch.interpret(context);
            }
        } else {
            return falseBranch.interpret(context);
        }

        return null;
    }
}
