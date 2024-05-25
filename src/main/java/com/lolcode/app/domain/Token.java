package com.lolcode.app.domain;

import lombok.*;

@Getter
@AllArgsConstructor
public class Token {
    private final Type type;
    private final String value;
    private final int line;
    
    public enum Type {
        KEYWORD, NUMBER, NEWLINE, IDENTIFIER, STRING
    }
}
