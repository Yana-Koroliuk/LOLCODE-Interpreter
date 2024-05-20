package com.lolcode.app.config;

import com.lolcode.app.application.VirtualMachine;
import com.lolcode.app.application.component.Compiler;
import com.lolcode.app.application.component.Interpreter;

/**
 * @author Mykhailo Balakhon
 * @link <a href="mailto:mykhailo.balakhon@communify.us">mykhailo.balakhon@communify.us</a>
 */
public class VirtualMachineBuilder {

    public VirtualMachine build() {
        return new VirtualMachine(
                new Compiler(),
                new Interpreter()
        );
    }
}
