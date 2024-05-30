/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

package com.lolcode.app.application.component.parser;

import com.lolcode.app.application.component.Interpreter;
import com.lolcode.app.application.component.parser.ASTnode.*;
import com.lolcode.app.domain.SyntaxTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    private Interpreter interpreter;
    private SyntaxTree syntaxTree;

    @BeforeEach
    void setUp() {
        interpreter = new Interpreter();
        syntaxTree = new SyntaxTree(new Program());
    }


    @Test
    void testVariableDeclaration() {
        // YARN
        ASTNode varDeclaration = new VariableDeclaration(
                "VAR", new Literal("YARN", "Hello"));
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "Hello");
        assertInstanceOf(String.class, interpreter.getContext().get("VAR"));


        // NUMBR
        varDeclaration = new VariableDeclaration(
                "VAR", new Literal("NUMBR", 1));
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1);
        assertInstanceOf(Integer.class, interpreter.getContext().get("VAR"));


        // NUMBAR
        varDeclaration = new VariableDeclaration(
                "VAR", new Literal("NUMBR", 1.5));
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1.5);
        assertInstanceOf(Double.class, interpreter.getContext().get("VAR"));


        // TROOF
        varDeclaration = new VariableDeclaration(
                "VAR", new Literal("TROOF", true));
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);
        assertInstanceOf(Boolean.class, interpreter.getContext().get("VAR"));


        // NOOB
        varDeclaration = new VariableDeclaration(
                "VAR", new Literal("NOOB", null));
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertNull(interpreter.getContext().get("VAR"));
    }

    @Test
    void testIdentifier() {
        ASTNode identifier = new Identifier("IDNT");
        ASTNode varDeclaration1 = new VariableDeclaration(
                "IDNT", new Literal("YARN", "transfer"));
        ASTNode varDeclaration2 = new VariableDeclaration("VAR", identifier);
        syntaxTree.getProgram().setBody(List.of(varDeclaration1, varDeclaration2));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "transfer");
    }

    @Test
    void testCasting() {
        // YARN to NUMBR
        ASTNode casting = new Casting(new Literal("YARN", "1"), "NUMBR");
        ASTNode varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1);
        assertInstanceOf(Integer.class, interpreter.getContext().get("VAR"));


        // YARN to NUMBR cuts off the tail
        casting = new Casting(new Literal("YARN", "1.5"), "NUMBR");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1);
        assertInstanceOf(Integer.class, interpreter.getContext().get("VAR"));


        // YARN to NUMBAR
        casting = new Casting(new Literal("YARN", "1.5"), "NUMBAR");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1.5);
        assertInstanceOf(Double.class, interpreter.getContext().get("VAR"));


        // NUMBR to YARN
        casting = new Casting(new Literal("NUMBR", 5), "YARN");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "5");
        assertInstanceOf(String.class, interpreter.getContext().get("VAR"));


        // YARN to TROOF
        ASTNode casting1 = new Casting(new Literal("YARN", "WIN"), "TROOF");
        ASTNode casting2 = new Casting(new Literal("YARN", "FAIL"), "TROOF");
        ASTNode varDeclaration1 = new VariableDeclaration("VAR1", casting1);
        ASTNode varDeclaration2 = new VariableDeclaration("VAR2", casting2);
        syntaxTree.getProgram().setBody(List.of(varDeclaration1, varDeclaration2));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR1"), true);
        assertEquals(interpreter.getContext().get("VAR2"), false);
        assertInstanceOf(Boolean.class, interpreter.getContext().get("VAR1"));
        assertInstanceOf(Boolean.class, interpreter.getContext().get("VAR2"));


        // YARN to NOOB
        casting = new Casting(new Literal("YARN", "anything"), "NOOB");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertNull(interpreter.getContext().get("VAR"));


        // Throws exceptions
        casting = new Casting(new Literal("YARN", "anything"), "UNKNOWN");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(IllegalArgumentException.class, () -> interpreter.interpret(syntaxTree));

        casting = new Casting(new Literal("YARN", "anything"), "NUMBR");
        varDeclaration = new VariableDeclaration("VAR", casting);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(RuntimeException.class, () -> interpreter.interpret(syntaxTree));
    }

    @Test
    void testConcatenation() {
        // YARNs
        ASTNode concatenation = new Concatenation(Arrays.asList(
                new Literal("YARN", "Hello "),
                new Literal("YARN", "World")
        ));
        ASTNode varDeclaration = new VariableDeclaration("VAR", concatenation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "Hello World");


        // YARNs + NUMBRs + NUMBARs
        concatenation = new Concatenation(Arrays.asList(
                new Literal("NUMBR", 1),
                new Literal("YARN", " "),
                new Literal("NUMBAR", 2.2)
        ));
        varDeclaration = new VariableDeclaration("VAR", concatenation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "1 2.2");


        // YARNs + TROOFs
        concatenation = new Concatenation(Arrays.asList(
                new Literal("TROOF", false),
                new Literal("YARN", " "),
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", concatenation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), "false true");
    }

    @Test
    void testMathOperation() {
        // +
        ASTNode mathOperation = new MathOperation("SUM OF", Arrays.asList(
                new Literal("NUMBR", 1),
                new Literal("NUMBR", 2)
        ));
        ASTNode varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 3.0);


        // -
        mathOperation = new MathOperation("DIFF OF", Arrays.asList(
                new Literal("NUMBR", 2),
                new Literal("NUMBR", 1)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 1.0);


        // *
        mathOperation = new MathOperation("PRODUKT OF", Arrays.asList(
                new Literal("NUMBAR", 4.5),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 9.0);


        // /
        mathOperation = new MathOperation("QUOSHUNT OF", Arrays.asList(
                new Literal("NUMBR", 8),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 4.0);


        // /0
        mathOperation = new MathOperation("QUOSHUNT OF", Arrays.asList(
                new Literal("NUMBR", 8),
                new Literal("NUMBR", 0)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(IllegalArgumentException.class, () -> interpreter.interpret(syntaxTree));

        // %0
        mathOperation = new MathOperation("MOD OF", Arrays.asList(
                new Literal("NUMBR", 4.3),
                new Literal("NUMBR", 0)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(IllegalArgumentException.class, () -> interpreter.interpret(syntaxTree));


        // BIGGR OF
        mathOperation = new MathOperation("BIGGR OF", Arrays.asList(
                new Literal("NUMBR", 8),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 8.0);


        // SMALLR OF
        mathOperation = new MathOperation("SMALLR OF", Arrays.asList(
                new Literal("NUMBR", 8),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), 2.0);


        // Throws exception
        mathOperation = new MathOperation("UNKNOWN", Arrays.asList(
                new Literal("NUMBR", 5),
                new Literal("NUMBR", 1)
        ));
        varDeclaration = new VariableDeclaration("VAR", mathOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(IllegalArgumentException.class, () -> interpreter.interpret(syntaxTree));
    }

    @Test
    void testBooleanOperation() {
        // BOTH OF
        ASTNode booleanOperation = new BooleanOperation("BOTH OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", false)
        ));
        ASTNode varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);

        booleanOperation = new BooleanOperation("BOTH OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);


        // EITHER OF
        booleanOperation = new BooleanOperation("EITHER OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", false)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);

        booleanOperation = new BooleanOperation("EITHER OF", Arrays.asList(
                new Literal("TROOF", false),
                new Literal("TROOF", false)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // WON OF
        booleanOperation = new BooleanOperation("WON OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", false)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);

        booleanOperation = new BooleanOperation("WON OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // NOT
        booleanOperation = new BooleanOperation("NOT", List.of(
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // ALL OF
        booleanOperation = new BooleanOperation("ALL OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", true),
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);

        booleanOperation = new BooleanOperation("ALL OF", Arrays.asList(
                new Literal("TROOF", true),
                new Literal("TROOF", false),
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // ANY OF
        booleanOperation = new BooleanOperation("ANY OF", Arrays.asList(
                new Literal("TROOF", false),
                new Literal("TROOF", true),
                new Literal("TROOF", false)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);

        booleanOperation = new BooleanOperation("ANY OF", Arrays.asList(
                new Literal("TROOF", false),
                new Literal("TROOF", false),
                new Literal("TROOF", false)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // BOTH SAEM
        booleanOperation = new BooleanOperation("BOTH SAEM", Arrays.asList(
                new Literal("NUMBR", 2),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);

        booleanOperation = new BooleanOperation("BOTH SAEM", Arrays.asList(
                new Literal("NUMBR", 2),
                new Literal("NUMBR", 3)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);


        // DIFFRINT
        booleanOperation = new BooleanOperation("DIFFRINT", Arrays.asList(
                new Literal("NUMBR", 2),
                new Literal("NUMBR", 2)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), false);

        booleanOperation = new BooleanOperation("DIFFRINT", Arrays.asList(
                new Literal("NUMBR", 2),
                new Literal("NUMBR", 3)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR"), true);


        // Throws exception
        booleanOperation = new BooleanOperation("UNKNOWN", List.of(
                new Literal("TROOF", true)
        ));
        varDeclaration = new VariableDeclaration("VAR", booleanOperation);
        syntaxTree.getProgram().setBody(List.of(varDeclaration));
        assertThrows(IllegalArgumentException.class, () -> interpreter.interpret(syntaxTree));
    }

    @Test
    void testAssignment() {
        ASTNode assignment = new Assignment("UNKNOWN",
                new Literal("YARN", "lol"
        ));
        syntaxTree.getProgram().setBody(List.of(assignment));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("UNKNOWN"), "lol");
    }

    @Test
    void testSwitch() {
        // ALL CASES
        ASTNode varDeclaration = new VariableDeclaration("STR0",
                new Literal("YARN", "R"));
        ASTNode expressionStatement = new ExpressionStatement(new Identifier("STR0"));
        ASTNode switchNode = new Switch(
                new Identifier("IT"),
                Arrays.asList(
                        new Case(
                                new Literal("YARN", "R"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR1",
                                                new Literal("YARN", "RED FISH"))))
                        ),
                        new Case(
                                new Literal("YARN", "Y"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR2",
                                                new Literal("YARN", "YELLOW FISH"))))
                        ),
                        new Case(
                                new Literal("YARN", "G"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR3",
                                                new Literal("YARN", "GREEN FISH"))))
                        )
                ),
                new DefaultCase(new Block(List.of(
                        new VariableDeclaration("VAR4",
                                new Literal("YARN", "UNKNOWN FISH")))))
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration, expressionStatement, switchNode));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR1"), "RED FISH");
        assertEquals(interpreter.getContext().get("VAR2"), "YELLOW FISH");
        assertEquals(interpreter.getContext().get("VAR3"), "GREEN FISH");
        assertNull(interpreter.getContext().get("VAR4"));


        // 2 cases
        interpreter = new Interpreter();
        varDeclaration = new VariableDeclaration("STR0",
                new Literal("YARN", "Y"));
        syntaxTree.getProgram().setBody(List.of(varDeclaration, expressionStatement, switchNode));
        interpreter.interpret(syntaxTree);
        assertNull(interpreter.getContext().get("VAR1"));
        assertEquals(interpreter.getContext().get("VAR2"), "YELLOW FISH");
        assertEquals(interpreter.getContext().get("VAR3"), "GREEN FISH");
        assertNull(interpreter.getContext().get("VAR4"));


        // default
        interpreter = new Interpreter();
        varDeclaration = new VariableDeclaration("STR0",
                new Literal("YARN", "O"));
        syntaxTree.getProgram().setBody(List.of(varDeclaration, expressionStatement, switchNode));
        interpreter.interpret(syntaxTree);
        assertNull(interpreter.getContext().get("VAR1"));
        assertNull(interpreter.getContext().get("VAR2"));
        assertNull(interpreter.getContext().get("VAR3"));
        assertEquals(interpreter.getContext().get("VAR4"), "UNKNOWN FISH");

        // with break
        interpreter = new Interpreter();
        varDeclaration = new VariableDeclaration("STR0",
                new Literal("YARN", "R"));
        switchNode = new Switch(
                new Identifier("IT"),
                Arrays.asList(
                        new Case(
                                new Literal("YARN", "R"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR1",
                                                new Literal("YARN", "RED FISH")),
                                        new Break()))
                        ),
                        new Case(
                                new Literal("YARN", "Y"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR2",
                                                new Literal("YARN", "YELLOW FISH"))))
                        ),
                        new Case(
                                new Literal("YARN", "G"),
                                new Block(List.of(
                                        new VariableDeclaration("VAR3",
                                                new Literal("YARN", "GREEN FISH"))))
                        )
                ),
                new DefaultCase(new Block(List.of(
                        new VariableDeclaration("VAR4",
                                new Literal("YARN", "UNKNOWN FISH")))))
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration, expressionStatement, switchNode));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("VAR1"), "RED FISH");
        assertNull(interpreter.getContext().get("VAR2"));
        assertNull(interpreter.getContext().get("VAR3"));
        assertNull(interpreter.getContext().get("VAR4"));
    }

    @Test
    void testLoops() {
        // UPPIN LOOP
        ASTNode varDeclaration = new VariableDeclaration("NUM",
                new Literal("NUMBR", 0));
        ASTNode loop = new Loop("LOOP", "UPPIN", new Identifier("VAR"),
                new BooleanOperation("BOTH SAEM",
                        List.of(new Identifier("VAR"), new Literal("NUMBR", 10))),
                new Block(List.of(new Assignment("NUM",
                        new MathOperation("SUM OF",
                                List.of(new Identifier("NUM"), new Literal("NUMBR", 1)))))
                )
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration, loop));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("NUM"), 10.0);


        // NERFIN LOOP
        interpreter = new Interpreter();
        varDeclaration = new VariableDeclaration("NUM",
                new Literal("NUMBR", 10));
        ASTNode variableDeclaration1 = new VariableDeclaration("VAR",
                new Literal("NUMBR", 10));
        loop = new Loop("LOOP", "NERFIN", new Identifier("VAR"),
                new BooleanOperation("BOTH SAEM",
                        List.of(new Identifier("VAR"), new Literal("NUMBR", 5))),
                new Block(List.of(new Assignment("NUM",
                        new MathOperation("SUM OF",
                                List.of(new Identifier("NUM"), new Literal("NUMBR", 1)))))
                )
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration, variableDeclaration1, loop));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("NUM"), 15.0);


        // loop break
        interpreter = new Interpreter();
        varDeclaration = new VariableDeclaration("NUM",
                new Literal("NUMBR", 0));
        loop = new Loop("LOOP", "UPPIN", new Identifier("VAR"),
                new BooleanOperation("BOTH SAEM",
                        List.of(new Identifier("VAR"), new Literal("NUMBR", 10))),
                new Block(List.of(new Assignment("NUM",
                        new MathOperation("SUM OF",
                                List.of(new Identifier("NUM"), new Literal("NUMBR", 1)))),
                        new Break())
                )
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration, loop));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("NUM"), 1.0);


        // Nested loops
        interpreter = new Interpreter();
        ASTNode varDeclaration1 = new VariableDeclaration("NUM1",
                new Literal("NUMBR", 0));
        ASTNode varDeclaration2 = new VariableDeclaration("NUM2",
                new Literal("NUMBR", 0));
        ASTNode innerLoop = new Loop("LOOP", "UPPIN", new Identifier("VAR2"),
                new BooleanOperation("BOTH SAEM",
                        List.of(new Identifier("VAR2"), new Literal("NUMBR", 3))),
                new Block(List.of(new Assignment("NUM2",
                                new MathOperation("SUM OF",
                                        List.of(new Identifier("NUM2"), new Literal("NUMBR", 1)))))
                )
        );
        ASTNode outerLoop = new Loop("LOOP", "UPPIN", new Identifier("VAR1"),
                new BooleanOperation("BOTH SAEM",
                        List.of(new Identifier("VAR1"), new Literal("NUMBR", 2))),
                new Block(List.of(new Assignment("NUM1",
                                new MathOperation("SUM OF",
                                        List.of(new Identifier("NUM1"), new Literal("NUMBR", 1)))),
                        innerLoop, new Assignment("VAR2", new Literal("NUMBR", 0)))
                )
        );
        syntaxTree.getProgram().setBody(List.of(varDeclaration1, varDeclaration2, outerLoop));
        interpreter.interpret(syntaxTree);
        assertEquals(interpreter.getContext().get("NUM1"), 2.0);
        assertEquals(interpreter.getContext().get("NUM2"), 6.0);
    }



}
