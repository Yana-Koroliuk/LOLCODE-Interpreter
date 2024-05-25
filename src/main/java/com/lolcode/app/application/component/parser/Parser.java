package com.lolcode.app.application.component.parser;

import com.lolcode.app.application.component.parser.ASTnode.*;
import com.lolcode.app.domain.SyntaxTree;
import com.lolcode.app.domain.Token;
import com.lolcode.app.domain.Tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Parser {

    private Tokens tokens;
    private int currentTokenIndex;

    private final Map<String, Supplier<ASTNode>> statementParsers;
    private final Map<String, Supplier<ASTNode>> expressionParsers;

    public Parser() {
        statementParsers = new HashMap<>();
        expressionParsers = new HashMap<>();

        statementParsers.put("I HAS A", this::parseVariableDeclaration);
        statementParsers.put("VISIBLE", this::parsePrintStatement);
        statementParsers.put("GIMMEH", this::parseInputStatement);
        statementParsers.put("O RLY?", this::parseConditional);
        statementParsers.put("KTHXBYE", this::parseEndProgram);

        addCommonParsers(statementParsers);
        addCommonParsers(expressionParsers);

        expressionParsers.put("WIN", () -> {
            consume(Token.Type.KEYWORD);
            return new Literal("TROOF", true);
        });
        expressionParsers.put("FAIL", () -> {
            consume(Token.Type.KEYWORD);
            return new Literal("TROOF", false);
        });
        expressionParsers.put("NOOB", () -> {
            consume(Token.Type.KEYWORD);
            return new Literal("NOOB", null);
        });
    }

    private void addCommonParsers(Map<String, Supplier<ASTNode>> parsers) {
        parsers.put("SMOOSH", this::parseConcatenation);
        parsers.put("MAEK", this::parseCasting);
        parsers.put("SUM OF", () -> parseMathOperation("SUM OF"));
        parsers.put("DIFF OF", () -> parseMathOperation("DIFF OF"));
        parsers.put("PRODUKT OF", () -> parseMathOperation("PRODUKT OF"));
        parsers.put("QUOSHUNT OF", () -> parseMathOperation("QUOSHUNT OF"));
        parsers.put("MOD OF", () -> parseMathOperation("MOD OF"));
        parsers.put("BIGGR OF", () -> parseMathOperation("BIGGR OF"));
        parsers.put("SMALLR OF", () -> parseMathOperation("SMALLR OF"));
        parsers.put("BOTH OF", () -> parseBooleanOperation("BOTH OF"));
        parsers.put("EITHER OF", () -> parseBooleanOperation("EITHER OF"));
        parsers.put("WON OF", () -> parseBooleanOperation("WON OF"));
        parsers.put("NOT", () -> parseBooleanOperation("NOT"));
        parsers.put("ALL OF", () -> parseBooleanOperation("ALL OF"));
        parsers.put("ANY OF", () -> parseBooleanOperation("ANY OF"));
        parsers.put("BOTH SAEM", () -> parseBooleanOperation("BOTH SAEM"));
        parsers.put("DIFFRINT", () -> parseBooleanOperation("DIFFRINT"));
    }

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
        Supplier<ASTNode> parser = statementParsers.get(currentToken.getValue());

        if (parser != null) {
            return parser.get();
        } else {
            return parseExpressionOrAssignment();
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

    private Print parsePrintStatement() {
        consume(Token.Type.KEYWORD);
        ASTNode value = parseExpression();

        if (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD && tokens.get(currentTokenIndex).getValue().equals("AN")) {
            List<ASTNode> values = new ArrayList<>();
            values.add(value);

            while (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD && tokens.get(currentTokenIndex).getValue().equals("AN")) {
                consume(Token.Type.KEYWORD);
                values.add(parseExpression());
            }

            if (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD && tokens.get(currentTokenIndex).getValue().equals("MKAY")) {
                consume(Token.Type.KEYWORD);
                value = new Concatenation(values);
            }
        }

        return new Print(value);
    }

    private EndProgram parseEndProgram() {
        consume(Token.Type.KEYWORD);
        return new EndProgram();
    }

    private Input parseInputStatement() {
        consume(Token.Type.KEYWORD);
        Token identifier = consume(Token.Type.IDENTIFIER);
        return new Input(identifier.getValue());
    }

    private ASTNode parseExpressionOrAssignment() {
        Token identifier = consume(Token.Type.IDENTIFIER);
        Token nextToken = tokens.get(currentTokenIndex);

        if (nextToken.getType() == Token.Type.KEYWORD && nextToken.getValue().equals("R")) {
            consume(Token.Type.KEYWORD);
            ASTNode value = parseExpression();
            return new Assignment(identifier.getValue(), value);
        } else {
            return new ExpressionStatement(new Identifier(identifier.getValue()));
        }
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
                Supplier<ASTNode> parser = expressionParsers.get(currentToken.getValue());
                if (parser != null) {
                    return parser.get();
                } else {
                    throw new IllegalArgumentException("Unknown keyword expression: " + currentToken.getValue());
                }
            default:
                throw new IllegalArgumentException("Unknown expression: " + currentToken.getValue());
        }
    }

    private Casting parseCasting() {
        consume(Token.Type.KEYWORD);
        ASTNode value = parseExpression();
        consume(Token.Type.KEYWORD);
        Token castType = consume(Token.Type.KEYWORD);
        return new Casting(value, castType.getValue());
    }

    private Concatenation parseConcatenation() {
        consume(Token.Type.KEYWORD);
        List<ASTNode> values = new ArrayList<>();
        values.add(parseExpression());

        while (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD &&
                tokens.get(currentTokenIndex).getValue().equals("AN")) {
            consume(Token.Type.KEYWORD);
            values.add(parseExpression());
        }

        consume(Token.Type.KEYWORD);
        return new Concatenation(values);
    }

    private MathOperation parseMathOperation(String operator) {
        consume(Token.Type.KEYWORD);
        ASTNode leftOperand = parseExpression();
        consume(Token.Type.KEYWORD);
        ASTNode rightOperand = parseExpression();
        List<ASTNode> operands = new ArrayList<>();
        operands.add(leftOperand);
        operands.add(rightOperand);
        return new MathOperation(operator, operands);
    }

    private BooleanOperation parseBooleanOperation(String operator) {
        consume(Token.Type.KEYWORD);
        List<ASTNode> operands = new ArrayList<>();
        operands.add(parseExpression());

        if (!operator.equals("NOT")) {
            consume(Token.Type.KEYWORD);
            operands.add(parseExpression());
        }

        while (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD &&
                tokens.get(currentTokenIndex).getValue().equals("AN")) {
            consume(Token.Type.KEYWORD);
            operands.add(parseExpression());
        }

        if (operator.equals("ALL OF") || operator.equals("ANY OF")) {
            consume(Token.Type.KEYWORD);
        }

        return new BooleanOperation(operator, operands);
    }

    private Conditional parseConditional() {
        consume(Token.Type.KEYWORD);
        consumeNewline();
        ASTNode condition = new Identifier("IT");

        consume(Token.Type.KEYWORD);
        consumeNewline();
        Block trueBranch = parseBlock();

        List<MebbeBranch> mebbeBranches = new ArrayList<>();
        while (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD && tokens.get(currentTokenIndex).getValue().equals("MEBBE")) {
            consume(Token.Type.KEYWORD);
            ASTNode mebbeCondition = parseExpression();
            consumeNewline();
            Block mebbeBody = parseBlock();
            mebbeBranches.add(new MebbeBranch(mebbeCondition, mebbeBody));
        }

        Block falseBranch = null;
        if (tokens.get(currentTokenIndex).getType() == Token.Type.KEYWORD && tokens.get(currentTokenIndex).getValue().equals("NO WAI")) {
            consume(Token.Type.KEYWORD);
            consumeNewline();
            falseBranch = parseBlock();
        }

        consume(Token.Type.KEYWORD);
        consumeNewline();
        return new Conditional(condition, trueBranch, mebbeBranches, falseBranch);
    }

    private Block parseBlock() {
        List<ASTNode> body = new ArrayList<>();
        while (tokens.get(currentTokenIndex).getType() == Token.Type.NEWLINE ||
                (tokens.get(currentTokenIndex).getType() != Token.Type.KEYWORD || (
                        !tokens.get(currentTokenIndex).getValue().equals("OIC") &&
                                !tokens.get(currentTokenIndex).getValue().equals("MEBBE") &&
                                !tokens.get(currentTokenIndex).getValue().equals("NO WAI")))) {
            if (tokens.get(currentTokenIndex).getType() == Token.Type.NEWLINE) {
                currentTokenIndex++;
                continue;
            }
            ASTNode statement = parseStatement();
            if (statement != null) {
                body.add(statement);
            }
        }
        return new Block(body);
    }

    private void consumeNewline() {
        if (tokens.get(currentTokenIndex).getType() == Token.Type.NEWLINE) {
            currentTokenIndex++;
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
