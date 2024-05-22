package com.lolcode.app.application.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolcode.app.domain.SourceCode;
import com.lolcode.app.domain.Tokens;
import com.lolcode.app.test.model.TestSourceCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LexerTest {
    private final Lexer lexer = new Lexer();

    private final ObjectMapper mapper = new ObjectMapper();

    private SourceCode sourceCode;
    private Tokens tokens;

    @Test
    void lex() throws JsonProcessingException {
        givenSourceCode("""
                HAI 1.2
                                
                I HAS A VAR            BTW VAR є nullінетипізований
                VAR
                VAR R "THREE"          BTW VAR тепер YARN і дорівнює "THREE"
                VAR R 3                BTW VAR2 тепер NUMBR і дорівнює 3
                VAR R 3.14             BTW VAR2 тепер NUMBAR і дорівнює 3.14
                VAR R WIN              BTW VAR2 тепер TROOF і дорівнює WIN
                VAR R NOOB             BTW VAR2 тепер NOOB
                """);

        whenLex();

        thenTokensAre("""
                [
                  { "type": "KEYWORD", "value": "HAI", "line": 1 },
                  { "type": "NUMBER", "value": "1.2", "line": 1 },
                  { "type": "NEWLINE", "value": "\\n", "line": 1 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 2 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 2 },
                  { "type": "NEWLINE", "value": "\\n", "line": 2 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 3 },
                  { "type": "NEWLINE", "value": "\\n", "line": 3 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 4 },
                  { "type": "KEYWORD", "value": "R", "line": 4 },
                  { "type": "STRING", "value": "\\"THREE\\"", "line": 4 },
                  { "type": "NEWLINE", "value": "\\n", "line": 4 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 5 },
                  { "type": "KEYWORD", "value": "R", "line": 5 },
                  { "type": "NUMBER", "value": 3, "line": 5 },
                  { "type": "NEWLINE", "value": "\\n", "line": 5 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 6 },
                  { "type": "KEYWORD", "value": "R", "line": 6 },
                  { "type": "NUMBER", "value": 3.14, "line": 6 },
                  { "type": "NEWLINE", "value": "\\n", "line": 6 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 7 },
                  { "type": "KEYWORD", "value": "R", "line": 7 },
                  { "type": "KEYWORD", "value": "WIN", "line": 7 },
                  { "type": "NEWLINE", "value": "\\n", "line": 7 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 8 },
                  { "type": "KEYWORD", "value": "R", "line": 8 },
                  { "type": "KEYWORD", "value": "NOOB", "line": 8 },
                  { "type": "NEWLINE", "value": "\\n", "line": 8 }
                ]
                """
        );
    }

    private void givenSourceCode(String code) {
        sourceCode = new TestSourceCode(code);
    }

    private void whenLex() {
        tokens = lexer.lex(sourceCode);
    }

    private void thenTokensAre(String json) throws JsonProcessingException {
        assertThat(tokens).containsExactlyElementsOf(mapper.readValue(json, Tokens.class));
    }
}
