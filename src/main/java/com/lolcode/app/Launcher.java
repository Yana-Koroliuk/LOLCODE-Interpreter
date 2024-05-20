package com.lolcode.app;

import com.lolcode.app.application.VirtualMachine;
import com.lolcode.app.config.VirtualMachineBuilder;
import com.lolcode.app.domain.FileSourceCode;

public class Launcher {
    
    public static void main(String[] args) {
        VirtualMachine app = new VirtualMachineBuilder().build();
        app.run(new FileSourceCode("cmd/src/main/resources/test.lol"));
    }
}
