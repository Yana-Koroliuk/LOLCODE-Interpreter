package com.lolcode.app.application.component.parser;

import com.lolcode.app.application.component.parser.ASTnode.*;
import com.lolcode.app.domain.SyntaxTree;
import com.lolcode.app.domain.Token;
import com.lolcode.app.domain.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Tokens tokens;
    private int currentTokenIndex;

    public SyntaxTree parse(Tokens tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        Program program = parseProgram();
        return new SyntaxTree(program);
    }

    private Program parseProgram() {
        List<ASTNode> body = new ArrayList<>();

        consume(Token.Type.KEYWORD);
        consume(Token.Type.NUMBER);
        consume(Token.Type.NEWLINE);

        while (currentTokenIndex < tokens.size()) {
            ASTNode statement = parseStatement();
            if (statement != null) {
                body.add(statement);
            }
        }
        return new Program(body);
    }

    private ASTNode parseStatement() {
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() == Token.Type.NEWLINE) {
            currentTokenIndex++;
        }

        if (currentTokenIndex >= tokens.size()) {
            return null;
        }

        Token currentToken = tokens.get(currentTokenIndex);
        switch (currentToken.getValue()) {
            case "I HAS A":
                return parseVariableDeclaration();
            case "KTHXBYE":
                return parseEndProgram();
            default:
                throw new IllegalArgumentException("Unknown statement: " + currentToken.getValue());
        }
    }

    private VariableDeclaration parseVariableDeclaration() {
        consume(Token.Type.KEYWORD);
        Token identifier = consume(Token.Type.IDENTIFIER);
        Token nextToken = tokens.get(currentTokenIndex);
        ASTNode value = null;

        if (nextToken.getType() == Token.Type.KEYWORD && nextToken.getValue().equals("ITZ")) {
            consume(Token.Type.KEYWORD);
            value = parseExpression();
        }

        return new VariableDeclaration(identifier.getValue(), value);
    }

    private EndProgram parseEndProgram() {
        consume(Token.Type.KEYWORD);
        return new EndProgram();
    }

    private ASTNode parseExpression() {
        Token currentToken = tokens.get(currentTokenIndex);
        switch (currentToken.getType()) {
            case IDENTIFIER:
                String identifierValue = currentToken.getValue();
                consume(Token.Type.IDENTIFIER);
                return new Identifier(identifierValue);
            case STRING:
                String stringValue = currentToken.getValue();
                consume(Token.Type.STRING);
                return new Literal("YARN", stringValue);
            case NUMBER:
                String numberValue = currentToken.getValue();
                consume(Token.Type.NUMBER);
                try {
                    return new Literal("NUMBR", Integer.parseInt(numberValue));
                } catch (NumberFormatException e) {
                    return new Literal("NUMBAR", Float.parseFloat(numberValue));
                }
            case KEYWORD:
                switch (currentToken.getValue()) {
                    case "WIN":
                        consume(Token.Type.KEYWORD);
                        return new Literal("TROOF", true);
                    case "FAIL":
                        consume(Token.Type.KEYWORD); // Consume "FAIL"
                        return new Literal("TROOF", false);
                    case "NOOB":
                        consume(Token.Type.KEYWORD); // Consume "NOOB"
                        return new Literal("NOOB", null);
                    default:
                        throw new IllegalArgumentException("Unknown keyword expression: " + currentToken.getValue());
                }
            default:
                throw new IllegalArgumentException("Unknown expression: " + currentToken.getValue());
        }
    }

    private Token consume(Token.Type expectedType) {
        Token token = tokens.get(currentTokenIndex);
        if (token.getType() != expectedType) {
            throw new IllegalArgumentException("Expected " + expectedType + " but found " + token.getType());
        }
        currentTokenIndex++;
        return token;
    }
}
