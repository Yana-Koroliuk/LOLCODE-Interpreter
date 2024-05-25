package com.lolcode.app.application;

import com.lolcode.app.application.component.Interpreter;
import com.lolcode.app.application.component.Lexer;
import com.lolcode.app.application.component.parser.Parser;
import com.lolcode.app.domain.SourceCode;
import com.lolcode.app.domain.SyntaxTree;
import com.lolcode.app.domain.Tokens;

public class VirtualMachine {
    private final Lexer lexer;
    private final Parser parser;
    private final Interpreter interpreter;

    public VirtualMachine(Lexer lexer, Parser parser, Interpreter interpreter) {
        this.lexer = lexer;
        this.parser = parser;
        this.interpreter = interpreter;
    }

    public void run(SourceCode sourceCode) {
        Tokens tokens = lexer.lex(sourceCode);
        SyntaxTree syntaxTree = parser.parse(tokens);
        interpreter.interpret(syntaxTree);
    }
}
