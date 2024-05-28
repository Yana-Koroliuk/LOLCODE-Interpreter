package com.lolcode.app.application.component;

import com.lolcode.app.domain.SourceCode;
import com.lolcode.app.domain.Token;
import com.lolcode.app.domain.Token.Type;
import com.lolcode.app.domain.Tokens;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            "(HAI|KTHXBYE|VISIBLE|GIMMEH|I HAS A|ITZ|R|SUM OF|DIFF OF|PRODUKT OF|QUOSHUNT OF|MOD OF|BIGGR OF|SMALLR OF|BOTH OF|EITHER OF|WON OF|NOT|ALL OF|ANY OF|WIN|NOOB|AN|SMOOSH|MKAY|MAEK|A|NUMBR|YARN|TROOF|BOTH SAEM|FAIL|DIFFRINT|NUMBAR|O RLY\\?|YA RLY|WTF\\?|NO WAI|OIC|MEBBE|OMG|GTFO|OMGWTF|IM IN YR|IM OUTTA YR|UPPIN|YR|TIL|HOW IZ I|IF U SAY SO|I IZ|FOUND YR)(?!\\w)|" +
            "\"([^\"]*)\"|" +
            "([0-9]+(?:\\.[0-9]+)?)|" +
            "([a-zA-Z_][a-zA-Z0-9_]*)|" +
            "(\\s+|,)|" +
            "(.)"
    );
    private static final Map<String, String> tokenValidators = Map.of(
            "HAI", "HAI 1\\.2",
            "GIMMEH", "GIMMEH \\w+",
            "I HAS A", "I HAS A(?= \\w+)",
            "ITZ", "I HAS A \\w+ ITZ (\\w+|\\\"\\w+\\\"|\\d+)",
            "GTFO", "(^ *|[^\\w ]|, )GTFO(?! *\\w)",
            "IM IN YR", "(^ *|[^\\w ]|, )IM IN YR \\w+",
            "IM OUTTA YR", "(^ *|[^\\w ]|, )IM OUTTA YR \\w+"
    );

    private Matcher matcher;

    public Tokens lex(SourceCode sourceCode) {
        Tokens tokens = new Tokens();
        List<String> lines = sourceCode.getLines();
        
        int lineCounter = 1;
        boolean comment = false;
        List<String> loops = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            // check comments
            if (line.contains("OBTW")) {
                comment = true;
                continue;
            }
            if (line.contains("TLDR")) {
                comment = false;
                continue;
            }
            if (comment && i == lines.size() - 1) {
                throw new IllegalArgumentException("Unclosed comment");
            }

            if (line.contains("BTW")) {
                line = line.substring(0, line.indexOf("BTW"));
            }
            if (comment || line.isBlank()) {
                continue;
            }
            
            // check loops
            if (line.contains("IM IN YR")) {
                Matcher loopMatcher = Pattern.compile("IM IN YR (\\w+)").matcher(line);
                if (!loopMatcher.find()) {
                    throw new IllegalArgumentException(MessageFormat.format(
                            "Illegal loop declaration at line {0}: {1}",
                            lineCounter, line
                    ));
                }
                loops.add(loopMatcher.group(1));
            }
            if (line.contains("IM OUTTA YR")) {
                Matcher loopMatcher = Pattern.compile("IM OUTTA YR (\\w+)").matcher(line);
                if (!loopMatcher.find()) {
                    throw new IllegalArgumentException(MessageFormat.format(
                            "Illegal loop close declaration at line {0}: {1}",
                            lineCounter, line
                    ));
                }
                if (!loops.contains(loopMatcher.group(1))) {
                    throw new IllegalArgumentException(MessageFormat.format(
                            "Trying to close not existing loop {0} at line {1}: {2}",
                            loopMatcher.group(1), lineCounter, line
                    ));
                }
                loops.remove(loopMatcher.group(1));
            }
            if (!loops.isEmpty() && i == lines.size() - 1) {
                loops.forEach(name -> {
                    throw new IllegalArgumentException("Unclosed loop " + name);
                });
            }
            
            matcher = tokenPatterns.matcher(line);
            while (matcher.find()) {
                if (is(KEYWORD)) {
                    if (tokenValidators.containsKey(get(KEYWORD))) {
                        Pattern validator = Pattern.compile(tokenValidators.get(get(KEYWORD)));
                        if (!validator.matcher(line).find()) {
                            throw new IllegalArgumentException(MessageFormat.format(
                                    "Illegal `{0}` keyword declaration at line {1}: {2}",
                                    get(KEYWORD), lineCounter, line
                            ));
                        }
                    }
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
                            "Unknown token `{0}` at line {1}: {2}",
                            get(UNKNOWN), lineCounter, line
                    ));
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
