package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Concatenation extends ASTNode {
    private List<ASTNode> values;

    public Concatenation(List<ASTNode> values) {
        super(ParseType.Concatenation);
        this.values = values;
    }

    @Override
    public String toString() {
        return "Concatenation{" +
                "values=" + values +
                '}';
    }
}
