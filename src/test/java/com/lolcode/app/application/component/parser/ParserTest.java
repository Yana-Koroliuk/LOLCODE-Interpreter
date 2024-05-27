package com.lolcode.app.application.component.parser;

import com.lolcode.app.application.component.parser.ASTnode.*;
import com.lolcode.app.domain.SyntaxTree;
import com.lolcode.app.domain.Token;
import com.lolcode.app.domain.Tokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    private Tokens createBaseTokens() {
        Tokens tokens = new Tokens();
        tokens.add(new Token(Token.Type.KEYWORD, "HAI", 1));
        tokens.add(new Token(Token.Type.NUMBER, "1.2", 1));
        tokens.add(new Token(Token.Type.NEWLINE, "\n", 1));
        tokens.add(new Token(Token.Type.KEYWORD, "KTHXBYE", 3));
        return tokens;
    }

    @Test
    public void testVariableDeclarationWithoutValue() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "I HAS A", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(VariableDeclaration.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        VariableDeclaration varDecl = (VariableDeclaration) program.getBody().get(0);
        assertEquals("VAR", varDecl.getName());
        assertNull(varDecl.getValue());
    }

    @Test
    public void testVariableDeclarationWithLiteralValue() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "I HAS A", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "STR", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "ITZ", 2));
        tokens.add(6, new Token(Token.Type.STRING, "\"Hello\"", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(VariableDeclaration.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        VariableDeclaration varDecl = (VariableDeclaration) program.getBody().get(0);
        assertEquals("STR", varDecl.getName());
        assertNotNull(varDecl.getValue());
        assertInstanceOf(Literal.class, varDecl.getValue());

        Literal literal = (Literal) varDecl.getValue();
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"Hello\"", literal.getValue());
    }

    @Test
    public void testVariableDeclarationWithCasting() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "I HAS A", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "NUMVAL", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "ITZ", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "MAEK", 2));
        tokens.add(7, new Token(Token.Type.IDENTIFIER, "STRVAL", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "A", 2));
        tokens.add(9, new Token(Token.Type.KEYWORD, "NUMBR", 2));
        tokens.add(10, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(VariableDeclaration.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        VariableDeclaration varDecl = (VariableDeclaration) program.getBody().get(0);
        assertEquals("NUMVAL", varDecl.getName());
        assertNotNull(varDecl.getValue());
        assertInstanceOf(Casting.class, varDecl.getValue());

        Casting casting = (Casting) varDecl.getValue();
        assertInstanceOf(Identifier.class, casting.getValue());
        assertEquals("STRVAL", ((Identifier) casting.getValue()).getName());
        assertEquals("NUMBR", casting.getCastTo());
    }

    @Test
    public void testVariableDeclarationWithBooleanOperation() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "I HAS A", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "BOOL1", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "ITZ", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "BOTH SAEM", 2));
        tokens.add(7, new Token(Token.Type.IDENTIFIER, "NUM1", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(9, new Token(Token.Type.NUMBER, "10", 2));
        tokens.add(10, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(VariableDeclaration.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        VariableDeclaration varDecl = (VariableDeclaration) program.getBody().get(0);
        assertEquals("BOOL1", varDecl.getName());
        assertNotNull(varDecl.getValue());
        assertInstanceOf(BooleanOperation.class, varDecl.getValue());

        BooleanOperation boolOp = (BooleanOperation) varDecl.getValue();
        assertEquals("BOTH SAEM", boolOp.getOperator());
        assertEquals(2, boolOp.getOperands().size());

        Identifier operand1 = (Identifier) boolOp.getOperands().get(0);
        assertEquals("NUM1", operand1.getName());

        Literal operand2 = (Literal) boolOp.getOperands().get(1);
        assertEquals("NUMBR", operand2.getValueType());
        assertEquals(10, operand2.getValue());
    }

    @Test
    public void testVariableDeclarationWithFunctionCall() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "I HAS A", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "RESULT", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "ITZ", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "I IZ", 2));
        tokens.add(7, new Token(Token.Type.IDENTIFIER, "ADD", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "YR", 2));
        tokens.add(9, new Token(Token.Type.NUMBER, "5", 2));
        tokens.add(10, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(11, new Token(Token.Type.KEYWORD, "YR", 2));
        tokens.add(12, new Token(Token.Type.NUMBER, "10", 2));
        tokens.add(13, new Token(Token.Type.KEYWORD, "MKAY", 2));
        tokens.add(14, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(VariableDeclaration.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        VariableDeclaration varDecl = (VariableDeclaration) program.getBody().get(0);
        assertEquals("RESULT", varDecl.getName());
        assertNotNull(varDecl.getValue());
        assertInstanceOf(FunctionCall.class, varDecl.getValue());

        FunctionCall functionCall = (FunctionCall) varDecl.getValue();
        assertEquals("ADD", functionCall.getName());
        assertEquals(2, functionCall.getArgs().size());

        Literal arg1 = (Literal) functionCall.getArgs().get(0);
        assertEquals("NUMBR", arg1.getValueType());
        assertEquals(5, arg1.getValue());

        Literal arg2 = (Literal) functionCall.getArgs().get(1);
        assertEquals("NUMBR", arg2.getValueType());
        assertEquals(10, arg2.getValue());
    }

    @Test
    public void testExpressionStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(4, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(ExpressionStatement.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        ExpressionStatement exprStmt = (ExpressionStatement) program.getBody().get(0);
        assertInstanceOf(Identifier.class, exprStmt.getExpression());
        assertEquals("VAR", ((Identifier) exprStmt.getExpression()).getName());
    }

    @Test
    public void testAssignment() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(4, new Token(Token.Type.KEYWORD, "R", 2));
        tokens.add(5, new Token(Token.Type.STRING, "\"THREE\"", 2));
        tokens.add(6, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Assignment.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Assignment assignment = (Assignment) program.getBody().get(0);
        assertEquals("VAR", assignment.getVariable());
        assertInstanceOf(Literal.class, assignment.getValue());

        Literal literal = (Literal) assignment.getValue();
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"THREE\"", literal.getValue());
    }

    @Test
    public void testInputStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "GIMMEH", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "INPUTVAR", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Input.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Input inputStmt = (Input) program.getBody().get(0);
        assertEquals("INPUTVAR", inputStmt.getName());
    }

    @Test
    public void testPrintStatementWithLiteral() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(4, new Token(Token.Type.STRING, "\"Hello, world!\"", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Print.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Print printStmt = (Print) program.getBody().get(0);
        assertInstanceOf(Literal.class, printStmt.getValue());

        Literal literal = (Literal) printStmt.getValue();
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"Hello, world!\"", literal.getValue());
    }

    @Test
    public void testPrintStatementWithIdentifier() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "INPUTVAR", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Print.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Print printStmt = (Print) program.getBody().get(0);
        assertInstanceOf(Identifier.class, printStmt.getValue());

        Identifier identifier = (Identifier) printStmt.getValue();
        assertEquals("INPUTVAR", identifier.getName());
    }

    @Test
    public void testPrintStatementWithConcatenation() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(4, new Token(Token.Type.STRING, "\"Var: \"", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.IDENTIFIER, "INPUTVAR", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Print.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Print printStmt = (Print) program.getBody().get(0);
        assertInstanceOf(Concatenation.class, printStmt.getValue());

        Concatenation concat = (Concatenation) printStmt.getValue();
        assertEquals(2, concat.getValues().size());

        Literal literal = (Literal) concat.getValues().get(0);
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"Var: \"", literal.getValue());

        Identifier identifier = (Identifier) concat.getValues().get(1);
        assertEquals("INPUTVAR", identifier.getName());
    }

    @Test
    public void testPrintStatementWithSmoosh() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(4, new Token(Token.Type.KEYWORD, "SMOOSH", 2));
        tokens.add(5, new Token(Token.Type.STRING, "\"Var: \"", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(7, new Token(Token.Type.IDENTIFIER, "INPUTVAR", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "MKAY", 2));
        tokens.add(9, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Print.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Print printStmt = (Print) program.getBody().get(0);
        assertInstanceOf(Concatenation.class, printStmt.getValue());

        Concatenation concat = (Concatenation) printStmt.getValue();
        assertEquals(2, concat.getValues().size());

        Literal literal = (Literal) concat.getValues().get(0);
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"Var: \"", literal.getValue());

        Identifier identifier = (Identifier) concat.getValues().get(1);
        assertEquals("INPUTVAR", identifier.getName());
    }

    @Test
    public void testConcatenationWithLiterals() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "SMOOSH", 2));
        tokens.add(4, new Token(Token.Type.STRING, "\"Hello\"", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.STRING, "\"World\"", 2));
        tokens.add(7, new Token(Token.Type.KEYWORD, "MKAY", 2));
        tokens.add(8, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Concatenation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Concatenation concat = (Concatenation) program.getBody().get(0);
        assertEquals(2, concat.getValues().size());

        Literal literal1 = (Literal) concat.getValues().get(0);
        assertEquals("YARN", literal1.getValueType());
        assertEquals("\"Hello\"", literal1.getValue());

        Literal literal2 = (Literal) concat.getValues().get(1);
        assertEquals("YARN", literal2.getValueType());
        assertEquals("\"World\"", literal2.getValue());
    }

    @Test
    public void testConcatenationWithLiteralsAndIdentifier() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "SMOOSH", 2));
        tokens.add(4, new Token(Token.Type.STRING, "\"SHello\"", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.STRING, "\" \"", 2));
        tokens.add(7, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(8, new Token(Token.Type.IDENTIFIER, "INPUTVAR", 2));
        tokens.add(9, new Token(Token.Type.KEYWORD, "MKAY", 2));
        tokens.add(10, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Concatenation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Concatenation concat = (Concatenation) program.getBody().get(0);
        assertEquals(3, concat.getValues().size());

        Literal literal1 = (Literal) concat.getValues().get(0);
        assertEquals("YARN", literal1.getValueType());
        assertEquals("\"SHello\"", literal1.getValue());

        Literal literal2 = (Literal) concat.getValues().get(1);
        assertEquals("YARN", literal2.getValueType());
        assertEquals("\" \"", literal2.getValue());

        Identifier identifier = (Identifier) concat.getValues().get(2);
        assertEquals("INPUTVAR", identifier.getName());
    }

    @Test
    public void testCastingWithLiteral() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "MAEK", 2));
        tokens.add(4, new Token(Token.Type.STRING, "\"123\"", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "A", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "NUMBR", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Casting.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Casting casting = (Casting) program.getBody().get(0);
        assertInstanceOf(Literal.class, casting.getValue());

        Literal literal = (Literal) casting.getValue();
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"123\"", literal.getValue());

        assertEquals("NUMBR", casting.getCastTo());
    }

    @Test
    public void testMathOperationWithLiterals() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "SUM OF", 2));
        tokens.add(4, new Token(Token.Type.NUMBER, "3", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.NUMBER, "5", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(MathOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        MathOperation mathOp = (MathOperation) program.getBody().get(0);
        assertEquals("SUM OF", mathOp.getOperator());
        assertEquals(2, mathOp.getOperands().size());

        Literal operand1 = (Literal) mathOp.getOperands().get(0);
        assertEquals("NUMBR", operand1.getValueType());
        assertEquals(3, operand1.getValue());

        Literal operand2 = (Literal) mathOp.getOperands().get(1);
        assertEquals("NUMBR", operand2.getValueType());
        assertEquals(5, operand2.getValue());
    }

    @Test
    public void testMathOperationWithLiteralAndIdentifier() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "SUM OF", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "NUM", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.NUMBER, "5", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(MathOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        MathOperation mathOp = (MathOperation) program.getBody().get(0);
        assertEquals("SUM OF", mathOp.getOperator());
        assertEquals(2, mathOp.getOperands().size());

        Identifier operand1 = (Identifier) mathOp.getOperands().get(0);
        assertEquals("NUM", operand1.getName());

        Literal operand2 = (Literal) mathOp.getOperands().get(1);
        assertEquals("NUMBR", operand2.getValueType());
        assertEquals(5, operand2.getValue());
    }

    @Test
    public void testMathOperationWithIdentifiers() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "SUM OF", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "NUM", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.IDENTIFIER, "INTEGER1", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(MathOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        MathOperation mathOp = (MathOperation) program.getBody().get(0);
        assertEquals("SUM OF", mathOp.getOperator());
        assertEquals(2, mathOp.getOperands().size());

        Identifier operand1 = (Identifier) mathOp.getOperands().get(0);
        assertEquals("NUM", operand1.getName());

        Identifier operand2 = (Identifier) mathOp.getOperands().get(1);
        assertEquals("INTEGER1", operand2.getName());
    }

    @Test
    public void testBooleanOperationWithLiterals() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "BOTH OF", 2));
        tokens.add(4, new Token(Token.Type.KEYWORD, "WIN", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "FAIL", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(BooleanOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        BooleanOperation boolOp = (BooleanOperation) program.getBody().get(0);
        assertEquals("BOTH OF", boolOp.getOperator());
        assertEquals(2, boolOp.getOperands().size());

        Literal operand1 = (Literal) boolOp.getOperands().get(0);
        assertEquals("TROOF", operand1.getValueType());
        assertEquals(true, operand1.getValue());

        Literal operand2 = (Literal) boolOp.getOperands().get(1);
        assertEquals("TROOF", operand2.getValueType());
        assertEquals(false, operand2.getValue());
    }

    @Test
    public void testBooleanOperationWithNot() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "NOT", 2));
        tokens.add(4, new Token(Token.Type.KEYWORD, "WIN", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(BooleanOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        BooleanOperation boolOp = (BooleanOperation) program.getBody().get(0);
        assertEquals("NOT", boolOp.getOperator());
        assertEquals(1, boolOp.getOperands().size());

        Literal operand = (Literal) boolOp.getOperands().get(0);
        assertEquals("TROOF", operand.getValueType());
        assertEquals(true, operand.getValue());
    }

    @Test
    public void testBooleanOperationWithAllOf() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "ALL OF", 2));
        tokens.add(4, new Token(Token.Type.KEYWORD, "WIN", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "FAIL", 2));
        tokens.add(7, new Token(Token.Type.KEYWORD, "MKAY", 2));
        tokens.add(8, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(BooleanOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        BooleanOperation boolOp = (BooleanOperation) program.getBody().get(0);
        assertEquals("ALL OF", boolOp.getOperator());
        assertEquals(2, boolOp.getOperands().size());

        Literal operand1 = (Literal) boolOp.getOperands().get(0);
        assertEquals("TROOF", operand1.getValueType());
        assertEquals(true, operand1.getValue());

        Literal operand2 = (Literal) boolOp.getOperands().get(1);
        assertEquals("TROOF", operand2.getValueType());
        assertEquals(false, operand2.getValue());
    }

    @Test
    public void testBooleanOperationWithBothSaem() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "BOTH SAEM", 2));
        tokens.add(4, new Token(Token.Type.NUMBER, "3", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(6, new Token(Token.Type.NUMBER, "3", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(BooleanOperation.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        BooleanOperation boolOp = (BooleanOperation) program.getBody().get(0);
        assertEquals("BOTH SAEM", boolOp.getOperator());
        assertEquals(2, boolOp.getOperands().size());

        Literal operand1 = (Literal) boolOp.getOperands().get(0);
        assertEquals("NUMBR", operand1.getValueType());
        assertEquals(3, operand1.getValue());

        Literal operand2 = (Literal) boolOp.getOperands().get(1);
        assertEquals("NUMBR", operand2.getValueType());
        assertEquals(3, operand2.getValue());
    }

    @Test
    public void testConditionalStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "O RLY?", 2));
        tokens.add(4, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "YA RLY", 2));
        tokens.add(6, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(7, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(8, new Token(Token.Type.STRING, "\"NUM is 10\"", 2));
        tokens.add(9, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(10, new Token(Token.Type.KEYWORD, "MEBBE", 2));
        tokens.add(11, new Token(Token.Type.KEYWORD, "BOTH SAEM", 2));
        tokens.add(12, new Token(Token.Type.IDENTIFIER, "NUM0", 2));
        tokens.add(13, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(14, new Token(Token.Type.NUMBER, "15", 2));
        tokens.add(15, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(16, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(17, new Token(Token.Type.STRING, "\"NUM is 15\"", 2));
        tokens.add(18, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(19, new Token(Token.Type.KEYWORD, "NO WAI", 2));
        tokens.add(20, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(21, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(22, new Token(Token.Type.STRING, "\"NUM is something else\"", 2));
        tokens.add(23, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(24, new Token(Token.Type.KEYWORD, "OIC", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Conditional.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Conditional conditional = (Conditional) program.getBody().get(0);
        assertInstanceOf(Identifier.class, conditional.getCondition());
        assertEquals("IT", ((Identifier) conditional.getCondition()).getName());

        Block trueBranch = conditional.getTrueBranch();
        assertEquals(1, trueBranch.getBody().size());
        assertInstanceOf(Print.class, trueBranch.getBody().get(0));
        Print truePrint = (Print) trueBranch.getBody().get(0);
        assertInstanceOf(Literal.class, truePrint.getValue());
        Literal trueLiteral = (Literal) truePrint.getValue();
        assertEquals("YARN", trueLiteral.getValueType());
        assertEquals("\"NUM is 10\"", trueLiteral.getValue());

        assertEquals(1, conditional.getMebbeBranches().size());
        MebbeBranch mebbeBranch = conditional.getMebbeBranches().get(0);
        assertInstanceOf(BooleanOperation.class, mebbeBranch.getCondition());
        BooleanOperation mebbeCondition = (BooleanOperation) mebbeBranch.getCondition();
        assertEquals("BOTH SAEM", mebbeCondition.getOperator());
        assertEquals(2, mebbeCondition.getOperands().size());
        Identifier mebbeOperand1 = (Identifier) mebbeCondition.getOperands().get(0);
        assertEquals("NUM0", mebbeOperand1.getName());
        Literal mebbeOperand2 = (Literal) mebbeCondition.getOperands().get(1);
        assertEquals("NUMBR", mebbeOperand2.getValueType());
        assertEquals(15, mebbeOperand2.getValue());

        Block mebbeBody = mebbeBranch.getBody();
        assertEquals(1, mebbeBody.getBody().size());
        assertInstanceOf(Print.class, mebbeBody.getBody().get(0));
        Print mebbePrint = (Print) mebbeBody.getBody().get(0);
        assertInstanceOf(Literal.class, mebbePrint.getValue());
        Literal mebbeLiteral = (Literal) mebbePrint.getValue();
        assertEquals("YARN", mebbeLiteral.getValueType());
        assertEquals("\"NUM is 15\"", mebbeLiteral.getValue());

        Block falseBranch = conditional.getFalseBranch();
        assertEquals(1, falseBranch.getBody().size());
        assertInstanceOf(Print.class, falseBranch.getBody().get(0));
        Print falsePrint = (Print) falseBranch.getBody().get(0);
        assertInstanceOf(Literal.class, falsePrint.getValue());
        Literal falseLiteral = (Literal) falsePrint.getValue();
        assertEquals("YARN", falseLiteral.getValueType());
        assertEquals("\"NUM is something else\"", falseLiteral.getValue());
    }

    @Test
    public void testSwitchStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "WTF?", 2));
        tokens.add(4, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "OMG", 2));
        tokens.add(6, new Token(Token.Type.STRING, "\"R\"", 2));
        tokens.add(7, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(9, new Token(Token.Type.STRING, "\"RED FISH\"", 2));
        tokens.add(10, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(11, new Token(Token.Type.KEYWORD, "GTFO", 2));
        tokens.add(12, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(13, new Token(Token.Type.KEYWORD, "OMG", 2));
        tokens.add(14, new Token(Token.Type.STRING, "\"Y\"", 2));
        tokens.add(15, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(16, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(17, new Token(Token.Type.STRING, "\"YELLOW FISH\"", 2));
        tokens.add(18, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(19, new Token(Token.Type.KEYWORD, "OMGWTF", 2));
        tokens.add(20, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(21, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(22, new Token(Token.Type.STRING, "\"FISH IS TRANSPARENT\"", 2));
        tokens.add(23, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(24, new Token(Token.Type.KEYWORD, "OIC", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Switch.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Switch switchStmt = (Switch) program.getBody().get(0);
        assertInstanceOf(Identifier.class, switchStmt.getCondition());
        assertEquals("IT", ((Identifier) switchStmt.getCondition()).getName());

        assertEquals(2, switchStmt.getCases().size());

        Case case1 = switchStmt.getCases().get(0);
        assertInstanceOf(Literal.class, case1.getValue());
        Literal case1Value = (Literal) case1.getValue();
        assertEquals("YARN", case1Value.getValueType());
        assertEquals("\"R\"", case1Value.getValue());
        assertEquals(2, case1.getBody().getBody().size());
        assertInstanceOf(Print.class, case1.getBody().getBody().get(0));
        assertInstanceOf(ConditionalBreak.class, case1.getBody().getBody().get(1));
        Print case1Print = (Print) case1.getBody().getBody().get(0);
        assertInstanceOf(Literal.class, case1Print.getValue());
        Literal case1PrintValue = (Literal) case1Print.getValue();
        assertEquals("YARN", case1PrintValue.getValueType());
        assertEquals("\"RED FISH\"", case1PrintValue.getValue());

        Case case2 = switchStmt.getCases().get(1);
        assertInstanceOf(Literal.class, case2.getValue());
        Literal case2Value = (Literal) case2.getValue();
        assertEquals("YARN", case2Value.getValueType());
        assertEquals("\"Y\"", case2Value.getValue());
        assertEquals(1, case2.getBody().getBody().size());
        assertInstanceOf(Print.class, case2.getBody().getBody().get(0));
        Print case2Print = (Print) case2.getBody().getBody().get(0);
        assertInstanceOf(Literal.class, case2Print.getValue());
        Literal case2PrintValue = (Literal) case2Print.getValue();
        assertEquals("YARN", case2PrintValue.getValueType());
        assertEquals("\"YELLOW FISH\"", case2PrintValue.getValue());

        DefaultCase defaultCase = switchStmt.getDefaultCase();
        assertNotNull(defaultCase);
        assertEquals(1, defaultCase.getBody().getBody().size());
        assertInstanceOf(Print.class, defaultCase.getBody().getBody().get(0));
        Print defaultPrint = (Print) defaultCase.getBody().getBody().get(0);
        assertInstanceOf(Literal.class, defaultPrint.getValue());
        Literal defaultPrintValue = (Literal) defaultPrint.getValue();
        assertEquals("YARN", defaultPrintValue.getValueType());
        assertEquals("\"FISH IS TRANSPARENT\"", defaultPrintValue.getValue());
    }

    @Test
    public void testSimpleLoopStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "IM IN YR", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "LOOP", 2));
        tokens.add(5, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(7, new Token(Token.Type.STRING, "\"Looping...\"", 2));
        tokens.add(8, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(9, new Token(Token.Type.KEYWORD, "GTFO", 2));
        tokens.add(10, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(11, new Token(Token.Type.KEYWORD, "IM OUTTA YR", 2));
        tokens.add(12, new Token(Token.Type.IDENTIFIER, "LOOP", 2));
        tokens.add(13, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Loop.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Loop loop = (Loop) program.getBody().get(0);
        assertEquals("LOOP", loop.getLabel());
        assertNull(loop.getOperation());
        assertNull(loop.getVariable());
        assertNull(loop.getCondition());

        Block body = loop.getBody();
        assertEquals(2, body.getBody().size());
        assertInstanceOf(Print.class, body.getBody().get(0));
        assertInstanceOf(ConditionalBreak.class, body.getBody().get(1));

        Print printStmt = (Print) body.getBody().get(0);
        assertInstanceOf(Literal.class, printStmt.getValue());
        Literal literal = (Literal) printStmt.getValue();
        assertEquals("YARN", literal.getValueType());
        assertEquals("\"Looping...\"", literal.getValue());

        ConditionalBreak breakStmt = (ConditionalBreak) body.getBody().get(1);
        assertInstanceOf(Identifier.class, breakStmt.getCondition());
        assertEquals("IT", ((Identifier) breakStmt.getCondition()).getName());
    }

    @Test
    public void testConditionalLoopStatement() {
        Tokens tokens = createBaseTokens();
        tokens.add(3, new Token(Token.Type.KEYWORD, "IM IN YR", 2));
        tokens.add(4, new Token(Token.Type.IDENTIFIER, "LOOP", 2));
        tokens.add(5, new Token(Token.Type.KEYWORD, "UPPIN", 2));
        tokens.add(6, new Token(Token.Type.KEYWORD, "YR", 2));
        tokens.add(7, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(8, new Token(Token.Type.KEYWORD, "TIL", 2));
        tokens.add(9, new Token(Token.Type.KEYWORD, "BOTH SAEM", 2));
        tokens.add(10, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(11, new Token(Token.Type.KEYWORD, "AN", 2));
        tokens.add(12, new Token(Token.Type.NUMBER, "10", 2));
        tokens.add(13, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(14, new Token(Token.Type.KEYWORD, "VISIBLE", 2));
        tokens.add(15, new Token(Token.Type.IDENTIFIER, "VAR", 2));
        tokens.add(16, new Token(Token.Type.NEWLINE, "\n", 2));
        tokens.add(17, new Token(Token.Type.KEYWORD, "IM OUTTA YR", 2));
        tokens.add(18, new Token(Token.Type.IDENTIFIER, "LOOP", 2));
        tokens.add(19, new Token(Token.Type.NEWLINE, "\n", 2));

        SyntaxTree syntaxTree = parser.parse(tokens);

        Program program = syntaxTree.getProgram();
        assertEquals(2, program.getBody().size());
        assertInstanceOf(Loop.class, program.getBody().get(0));
        assertInstanceOf(EndProgram.class, program.getBody().get(1));

        Loop loop = (Loop) program.getBody().get(0);
        assertEquals("LOOP", loop.getLabel());
        assertEquals("UPPIN", loop.getOperation());
        assertNotNull(loop.getVariable());
        assertEquals("VAR", ((Identifier) loop.getVariable()).getName());
        assertNotNull(loop.getCondition());

        BooleanOperation condition = (BooleanOperation) loop.getCondition();
        assertEquals("BOTH SAEM", condition.getOperator());
        assertEquals(2, condition.getOperands().size());
        Identifier operand1 = (Identifier) condition.getOperands().get(0);
        assertEquals("VAR", operand1.getName());
        Literal operand2 = (Literal) condition.getOperands().get(1);
        assertEquals("NUMBR", operand2.getValueType());
        assertEquals(10, operand2.getValue());

        Block body = loop.getBody();
        assertEquals(1, body.getBody().size());
        assertInstanceOf(Print.class, body.getBody().get(0));

        Print printStmt = (Print) body.getBody().get(0);
        assertInstanceOf(Identifier.class, printStmt.getValue());
        assertEquals("VAR", ((Identifier) printStmt.getValue()).getName());
    }

}

