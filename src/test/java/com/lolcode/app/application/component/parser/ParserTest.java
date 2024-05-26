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
}
