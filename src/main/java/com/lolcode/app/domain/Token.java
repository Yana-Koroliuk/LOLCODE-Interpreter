package com.lolcode.app.domain;

/**
 * @author Mykhailo Balakhon
 * @link <a href="mailto:mykhailo.balakhon@communify.us">mykhailo.balakhon@communify.us</a>
 */
public record Token(Type type, String value, int line) {

    public enum Type {
        KEYWORD, NUMBER, NEWLINE, IDENTIFIER, STRING
    }
}
