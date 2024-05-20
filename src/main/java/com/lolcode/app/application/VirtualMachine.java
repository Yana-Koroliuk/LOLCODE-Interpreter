package com.lolcode.app.application;

import com.lolcode.app.application.component.Compiler;
import com.lolcode.app.application.component.Interpreter;
import com.lolcode.app.domain.ByteCode;
import com.lolcode.app.domain.SourceCode;

/**
 * @author Mykhailo Balakhon
 * @link <a href="mailto:mykhailo.balakhon@communify.us">mykhailo.balakhon@communify.us</a>
 */
public class VirtualMachine {
    private final Compiler compiler;
    private final Interpreter interpreter;

    public VirtualMachine(Compiler compiler, Interpreter interpreter) {
        this.compiler = compiler;
        this.interpreter = interpreter;
    }

    public void run(SourceCode sourceCode) {
        ByteCode byteCode = compiler.compile(sourceCode);
        interpreter.interpret(byteCode);
    }
}
