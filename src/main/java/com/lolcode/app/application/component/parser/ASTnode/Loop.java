package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Loop extends ASTNode {
    private String label;
    private List<ASTNode> body;

    public Loop(String label, List<ASTNode> body) {
        super(ParseType.Loop);
        this.label = label;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Loop{" +
                "label='" + label + '\'' +
                ", body=" + body +
                '}';
    }
}
