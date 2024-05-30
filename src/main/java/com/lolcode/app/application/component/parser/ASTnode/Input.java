package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.interpreter.Context;
import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

import java.util.Scanner;

@Getter
@Setter
public class Input extends ASTNode {
    private final String name;
    private final Scanner scanner;

    public Input(String name) {
        super(ParseType.INPUT);
        this.name = name;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String toString() {
        return "Input{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public Object interpret(Context context) {
        String input = scanner.nextLine();
        if (name == null) {
            context.put("IT", input);
        } else {
            context.put(name, input);
        }
        return input;
    }
}
