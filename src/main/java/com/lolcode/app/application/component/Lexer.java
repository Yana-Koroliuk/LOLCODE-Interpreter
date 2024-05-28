package com.lolcode.app.application.component;

import com.lolcode.app.domain.SourceCode;
import com.lolcode.app.domain.Token;
import com.lolcode.app.domain.Token.Type;
import com.lolcode.app.domain.Tokens;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lolcode.app.application.component.Lexer.Index.IDENTIFIER;
import static com.lolcode.app.application.component.Lexer.Index.KEYWORD;
import static com.lolcode.app.application.component.Lexer.Index.NUMBER;
import static com.lolcode.app.application.component.Lexer.Index.STRING;
import static com.lolcode.app.application.component.Lexer.Index.UNKNOWN;
import static com.lolcode.app.application.component.Lexer.Index.WHITESPACE;

public class Lexer {
    private static final Pattern tokenPatterns = Pattern.compile(
            "(HAI|KTHXBYE|OBTW|TLDR|VISIBLE|GIMMEH|I HAS A|ITZ|R|SUM OF|DIFF OF|PRODUKT OF|QUOSHUNT OF|MOD OF|BIGGR OF|SMALLR OF|BOTH OF|EITHER OF|WON OF|NOT|ALL OF|ANY OF|WIN|NOOB|AN|SMOOSH|MKAY|MAEK|A|NUMBR|YARN|TROOF|BOTH SAEM|FAIL|DIFFRINT|NUMBAR|O RLY\\?|YA RLY|WTF\\?|NO WAI|OIC|MEBBE|OMG|GTFO|OMGWTF|IM IN YR|IM OUTTA YR|UPPIN|YR|TIL|HOW IZ I|IF U SAY SO|I IZ|FOUND YR)(?!\\w)|" +
            "\"([^\"]*)\"|" +
            "([0-9]+(?:\\.[0-9]+)?)|" +
            "([a-zA-Z_][a-zA-Z0-9_]*)|" +
            "(\\s+|,)|" +
            "(.)"
    );
    private int lineCounter = 1;
    private Matcher matcher;

    public Tokens lex(SourceCode sourceCode) {
        Tokens tokens = new Tokens();
        List<String> lines = sourceCode.getLines();

        for (String line : lines) {
            if (line.contains("BTW")) {
                line = line.substring(0, line.indexOf("BTW"));
            }
            if (line.isBlank()) {
                continue;
            }
            matcher = tokenPatterns.matcher(line);
            while (matcher.find()) {
                if (is(KEYWORD)) {
                    tokens.add(new Token(Type.KEYWORD, get(KEYWORD), lineCounter));
                }
                if (is(STRING)) {
                    tokens.add(new Token(Type.STRING, "\"" + get(STRING) + "\"", lineCounter));
                }
                if (is(NUMBER)) {
                    tokens.add(new Token(Type.NUMBER, get(NUMBER), lineCounter));
                }
                if (is(IDENTIFIER)) {
                    tokens.add(new Token(Type.IDENTIFIER, get(IDENTIFIER), lineCounter));
                }
                if (is(WHITESPACE)) {
                    //skip
                }
                if (is(UNKNOWN)) {
                    throw new IllegalArgumentException(MessageFormat.format(
                            "Unknown token at line {0}: {1}",
                            lineCounter, get(UNKNOWN))
                    );
                }
            }
            tokens.add(new Token(Type.NEWLINE, "\n", lineCounter++));
        }
        return tokens;
    }

    private boolean is(Index index) {
        return get(index) != null;
    }

    private String get(Index index) {
        return matcher.group(index.index);
    }

    public enum Index {
        KEYWORD(1), STRING(2), NUMBER(3), IDENTIFIER(4), WHITESPACE(5), UNKNOWN(6);

        private final int index;

        Index(int index) {
            this.index = index;
        }
    }
}
