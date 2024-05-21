package com.lolcode.app.config;

import com.lolcode.app.application.VirtualMachine;
import com.lolcode.app.application.component.Interpreter;
import com.lolcode.app.application.component.Lexer;
import com.lolcode.app.application.component.Parser;

public class VirtualMachineBuilder {

    public VirtualMachine build() {
        return new VirtualMachine(
                new Lexer(),
                new Parser(),
                new Interpreter()
        );
    }
}
