package com.lolcode.app.domain;

public record Token(Type type, String value, int line) {

    public enum Type {
        KEYWORD, NUMBER, NEWLINE, IDENTIFIER, STRING
    }
}
