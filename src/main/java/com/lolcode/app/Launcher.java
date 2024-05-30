package com.lolcode.app;

import com.lolcode.app.application.VirtualMachine;
import com.lolcode.app.config.VirtualMachineBuilder;
import com.lolcode.app.domain.FileSourceCode;

public class Launcher {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide the path to the LOLCODE file.");
            System.exit(1);
        }

        String filePath = args[0];
        VirtualMachine app = new VirtualMachineBuilder().build();
        app.run(new FileSourceCode(filePath));
    }
}