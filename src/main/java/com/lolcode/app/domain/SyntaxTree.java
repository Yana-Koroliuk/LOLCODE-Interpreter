package com.lolcode.app.domain;

import com.lolcode.app.application.component.parser.ASTnode.Program;
import lombok.Getter;

@Getter
public class SyntaxTree {
    private final Program program;

    public SyntaxTree(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return program.toString();
    }
}
