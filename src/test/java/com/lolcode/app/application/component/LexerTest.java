package com.lolcode.app.application.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolcode.app.domain.SourceCode;
import com.lolcode.app.domain.Tokens;
import com.lolcode.app.test.model.TestSourceCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LexerTest {
    private final Lexer lexer = new Lexer();

    private final ObjectMapper mapper = new ObjectMapper();

    private SourceCode sourceCode;
    private Tokens tokens;
    
    @ParameterizedTest
    @MethodSource("illegalHaiKthxbueDeclaration")
    void lexShouldThrowIfProcedureIsNonOpenedOrClosed(String code) {
        givenSourceCode(code);

        assertThatThrownBy(this::whenLex)
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void lexVariables() throws JsonProcessingException {
        givenSourceCode("""
                HAI 1.2
                                
                I HAS A VAR            BTW VAR є nullінетипізований
                VAR
                VAR R "THREE"          BTW VAR тепер YARN і дорівнює "THREE"
                VAR R 3                BTW VAR2 тепер NUMBR і дорівнює 3
                VAR R 3.14             BTW VAR2 тепер NUMBAR і дорівнює 3.14
                VAR R WIN              BTW VAR2 тепер TROOF і дорівнює WIN
                VAR R NOOB             BTW VAR2 тепер NOOB
                                
                I HAS A STR ITZ "Hello"  BTW правильно оголошення змінної
                I HAS A NUM ITZ 10
                I HAS A FLT ITZ 3.14
                I HAS A BOOL ITZ WIN
                I HAS A INPUTVAR
                                
                GIMMEH INPUTVAR
                                
                VISIBLE "Hello, world!"
                VISIBLE INPUTVAR
                VISIBLE "Var: " AN INPUTVAR
                VISIBLE SMOOSH "Var: " AN INPUTVAR MKAY
                                
                SMOOSH "Hello" AN "World" MKAY
                SMOOSH "SHello" AN " " AN INPUTVAR MKAY
                VISIBLE IT
                                
                MAEK "6" A NUMBR
                I HAS A STRVAL ITZ "456"
                I HAS A NUMVAL ITZ MAEK STRVAL A NUMBR
                I HAS A NUMVAL1 ITZ 159
                I HAS A STRVAL1 ITZ MAEK NUMVAL1 A YARN
                I HAS A STRVAL2 ITZ "WIN"
                I HAS A BOOLVAL ITZ MAEK STRVAL2 A TROOF
                I HAS A INTEGER ITZ 42
                I HAS A FLOATVAL ITZ MAEK INTEGER A NUMBAR
                I HAS A FLOATVAL1 ITZ 3.14
                I HAS A INTEGER1 ITZ MAEK FLOATVAL1 A NUMBR
                                
                SUM OF 3 AN 5          BTW 3 + 5
                SUM OF NUM AN 5        BTW 3 + 5
                SUM OF NUM AN INTEGER1  BTW 3 + 5
                SUM OF 3 AN 5          BTW 3 + 5
                DIFF OF 10 AN 4        BTW 10 - 4
                PRODUKT OF 6 AN 7      BTW 6 * 7
                QUOSHUNT OF 20 AN 4    BTW 20 / 4
                MOD OF 10 AN 3         BTW 10 % 3
                BIGGR OF 4 AN 9        BTW max(4, 9)
                SMALLR OF 4 AN 9       BTW min(4, 9)
                                
                BOTH OF WIN AN FAIL         BTW WIN iff x=WIN, y=WIN
                EITHER OF WIN AN FAIL       BTW FAIL iff x=FAIL, y=FAIL
                WON OF WIN AN FAIL          BTW FAIL iff x=y
                NOT WIN                     BTW WIN iff x=FAIL
                ALL OF WIN AN FAIL MKAY     BTW infinite arity AND
                ANY OF WIN AN FAIL MKAY     BTW infinite arity OR
                                
                BOTH SAEM 3 AN 3            BTW WIN iff x == y
                DIFFRINT 3 AN 4             BTW WIN iff x != y
                                
                BTW Example:
                I HAS A NUM1 ITZ 10
                I HAS A NUM2 ITZ 20
                I HAS A BOOL1 ITZ BOTH SAEM NUM1 AN 10      BTW BOOL1 буде WIN, якщо NUM1 дорівнює 10
                I HAS A BOOL2 ITZ DIFFRINT NUM2 AN 20       BTW BOOL2 буде FAIL, якщо NUM2 дорівнює 20
                I HAS A RESULT1 ITZ BOTH OF BOOL1 AN BOOL2         BTW WIN iff BOOL1=WIN, BOOL2=WIN
                
                KTHXBYE
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
                  { "type": "NEWLINE", "value": "\\n", "line": 8 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 9 },
                  { "type": "IDENTIFIER", "value": "STR", "line": 9 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 9 },
                  { "type": "STRING", "value": "\\"Hello\\"", "line": 9 },
                  { "type": "NEWLINE", "value": "\\n", "line": 9 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 10 },
                  { "type": "IDENTIFIER", "value": "NUM", "line": 10 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 10 },
                  { "type": "NUMBER", "value": 10, "line": 10 },
                  { "type": "NEWLINE", "value": "\\n", "line": 10 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 11 },
                  { "type": "IDENTIFIER", "value": "FLT", "line": 11 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 11 },
                  { "type": "NUMBER", "value": 3.14, "line": 11 },
                  { "type": "NEWLINE", "value": "\\n", "line": 11 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 12 },
                  { "type": "IDENTIFIER", "value": "BOOL", "line": 12 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 12 },
                  { "type": "KEYWORD", "value": "WIN", "line": 12 },
                  { "type": "NEWLINE", "value": "\\n", "line": 12 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 13 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 13 },
                  { "type": "NEWLINE", "value": "\\n", "line": 13 },
                  { "type": "KEYWORD", "value": "GIMMEH", "line": 14 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 14 },
                  { "type": "NEWLINE", "value": "\\n", "line": 14 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 15 },
                  { "type": "STRING", "value": "\\"Hello, world!\\"", "line": 15 },
                  { "type": "NEWLINE", "value": "\\n", "line": 15 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 16 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 16 },
                  { "type": "NEWLINE", "value": "\\n", "line": 16 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 17 },
                  { "type": "STRING", "value": "\\"Var: \\"", "line": 17 },
                  { "type": "KEYWORD", "value": "AN", "line": 17 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 17 },
                  { "type": "NEWLINE", "value": "\\n", "line": 17 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 18 },
                  { "type": "KEYWORD", "value": "SMOOSH", "line": 18 },
                  { "type": "STRING", "value": "\\"Var: \\"", "line": 18 },
                  { "type": "KEYWORD", "value": "AN", "line": 18 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 18 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 18 },
                  { "type": "NEWLINE", "value": "\\n", "line": 18 },
                  { "type": "KEYWORD", "value": "SMOOSH", "line": 19 },
                  { "type": "STRING", "value": "\\"Hello\\"", "line": 19 },
                  { "type": "KEYWORD", "value": "AN", "line": 19 },
                  { "type": "STRING", "value": "\\"World\\"", "line": 19 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 19 },
                  { "type": "NEWLINE", "value": "\\n", "line": 19 },
                  { "type": "KEYWORD", "value": "SMOOSH", "line": 20 },
                  { "type": "STRING", "value": "\\"SHello\\"", "line": 20 },
                  { "type": "KEYWORD", "value": "AN", "line": 20 },
                  { "type": "STRING", "value": "\\" \\"", "line": 20 },
                  { "type": "KEYWORD", "value": "AN", "line": 20 },
                  { "type": "IDENTIFIER", "value": "INPUTVAR", "line": 20 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 20 },
                  { "type": "NEWLINE", "value": "\\n", "line": 20 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 21 },
                  { "type": "IDENTIFIER", "value": "IT", "line": 21 },
                  { "type": "NEWLINE", "value": "\\n", "line": 21 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 22 },
                  { "type": "STRING", "value": "\\"6\\"", "line": 22 },
                  { "type": "KEYWORD", "value": "A", "line": 22 },
                  { "type": "KEYWORD", "value": "NUMBR", "line": 22 },
                  { "type": "NEWLINE", "value": "\\n", "line": 22 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 23 },
                  { "type": "IDENTIFIER", "value": "STRVAL", "line": 23 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 23 },
                  { "type": "STRING", "value": "\\"456\\"", "line": 23 },
                  { "type": "NEWLINE", "value": "\\n", "line": 23 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 24 },
                  { "type": "IDENTIFIER", "value": "NUMVAL", "line": 24 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 24 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 24 },
                  { "type": "IDENTIFIER", "value": "STRVAL", "line": 24 },
                  { "type": "KEYWORD", "value": "A", "line": 24 },
                  { "type": "KEYWORD", "value": "NUMBR", "line": 24 },
                  { "type": "NEWLINE", "value": "\\n", "line": 24 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 25 },
                  { "type": "IDENTIFIER", "value": "NUMVAL1", "line": 25 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 25 },
                  { "type": "NUMBER", "value": 159, "line": 25 },
                  { "type": "NEWLINE", "value": "\\n", "line": 25 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 26 },
                  { "type": "IDENTIFIER", "value": "STRVAL1", "line": 26 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 26 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 26 },
                  { "type": "IDENTIFIER", "value": "NUMVAL1", "line": 26 },
                  { "type": "KEYWORD", "value": "A", "line": 26 },
                  { "type": "KEYWORD", "value": "YARN", "line": 26 },
                  { "type": "NEWLINE", "value": "\\n", "line": 26 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 27 },
                  { "type": "IDENTIFIER", "value": "STRVAL2", "line": 27 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 27 },
                  { "type": "STRING", "value": "\\"WIN\\"", "line": 27 },
                  { "type": "NEWLINE", "value": "\\n", "line": 27 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 28 },
                  { "type": "IDENTIFIER", "value": "BOOLVAL", "line": 28 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 28 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 28 },
                  { "type": "IDENTIFIER", "value": "STRVAL2", "line": 28 },
                  { "type": "KEYWORD", "value": "A", "line": 28 },
                  { "type": "KEYWORD", "value": "TROOF", "line": 28 },
                  { "type": "NEWLINE", "value": "\\n", "line": 28 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 29 },
                  { "type": "IDENTIFIER", "value": "INTEGER", "line": 29 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 29 },
                  { "type": "NUMBER", "value": 42, "line": 29 },
                  { "type": "NEWLINE", "value": "\\n", "line": 29 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 30 },
                  { "type": "IDENTIFIER", "value": "FLOATVAL", "line": 30 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 30 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 30 },
                  { "type": "IDENTIFIER", "value": "INTEGER", "line": 30 },
                  { "type": "KEYWORD", "value": "A", "line": 30 },
                  { "type": "KEYWORD", "value": "NUMBAR", "line": 30 },
                  { "type": "NEWLINE", "value": "\\n", "line": 30 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 31 },
                  { "type": "IDENTIFIER", "value": "FLOATVAL1", "line": 31 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 31 },
                  { "type": "NUMBER", "value": 3.14, "line": 31 },
                  { "type": "NEWLINE", "value": "\\n", "line": 31 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 32 },
                  { "type": "IDENTIFIER", "value": "INTEGER1", "line": 32 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 32 },
                  { "type": "KEYWORD", "value": "MAEK", "line": 32 },
                  { "type": "IDENTIFIER", "value": "FLOATVAL1", "line": 32 },
                  { "type": "KEYWORD", "value": "A", "line": 32 },
                  { "type": "KEYWORD", "value": "NUMBR", "line": 32 },
                  { "type": "NEWLINE", "value": "\\n", "line": 32 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 33 },
                  { "type": "NUMBER", "value": 3, "line": 33 },
                  { "type": "KEYWORD", "value": "AN", "line": 33 },
                  { "type": "NUMBER", "value": 5, "line": 33 },
                  { "type": "NEWLINE", "value": "\\n", "line": 33 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 34 },
                  { "type": "IDENTIFIER", "value": "NUM", "line": 34 },
                  { "type": "KEYWORD", "value": "AN", "line": 34 },
                  { "type": "NUMBER", "value": 5, "line": 34 },
                  { "type": "NEWLINE", "value": "\\n", "line": 34 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 35 },
                  { "type": "IDENTIFIER", "value": "NUM", "line": 35 },
                  { "type": "KEYWORD", "value": "AN", "line": 35 },
                  { "type": "IDENTIFIER", "value": "INTEGER1", "line": 35 },
                  { "type": "NEWLINE", "value": "\\n", "line": 35 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 36 },
                  { "type": "NUMBER", "value": 3, "line": 36 },
                  { "type": "KEYWORD", "value": "AN", "line": 36 },
                  { "type": "NUMBER", "value": 5, "line": 36 },
                  { "type": "NEWLINE", "value": "\\n", "line": 36 },
                  { "type": "KEYWORD", "value": "DIFF OF", "line": 37 },
                  { "type": "NUMBER", "value": 10, "line": 37 },
                  { "type": "KEYWORD", "value": "AN", "line": 37 },
                  { "type": "NUMBER", "value": 4, "line": 37 },
                  { "type": "NEWLINE", "value": "\\n", "line": 37 },
                  { "type": "KEYWORD", "value": "PRODUKT OF", "line": 38 },
                  { "type": "NUMBER", "value": 6, "line": 38 },
                  { "type": "KEYWORD", "value": "AN", "line": 38 },
                  { "type": "NUMBER", "value": 7, "line": 38 },
                  { "type": "NEWLINE", "value": "\\n", "line": 38 },
                  { "type": "KEYWORD", "value": "QUOSHUNT OF", "line": 39 },
                  { "type": "NUMBER", "value": 20, "line": 39 },
                  { "type": "KEYWORD", "value": "AN", "line": 39 },
                  { "type": "NUMBER", "value": 4, "line": 39 },
                  { "type": "NEWLINE", "value": "\\n", "line": 39 },
                  { "type": "KEYWORD", "value": "MOD OF", "line": 40 },
                  { "type": "NUMBER", "value": 10, "line": 40 },
                  { "type": "KEYWORD", "value": "AN", "line": 40 },
                  { "type": "NUMBER", "value": 3, "line": 40 },
                  { "type": "NEWLINE", "value": "\\n", "line": 40 },
                  { "type": "KEYWORD", "value": "BIGGR OF", "line": 41 },
                  { "type": "NUMBER", "value": 4, "line": 41 },
                  { "type": "KEYWORD", "value": "AN", "line": 41 },
                  { "type": "NUMBER", "value": 9, "line": 41 },
                  { "type": "NEWLINE", "value": "\\n", "line": 41 },
                  { "type": "KEYWORD", "value": "SMALLR OF", "line": 42 },
                  { "type": "NUMBER", "value": 4, "line": 42 },
                  { "type": "KEYWORD", "value": "AN", "line": 42 },
                  { "type": "NUMBER", "value": 9, "line": 42 },
                  { "type": "NEWLINE", "value": "\\n", "line": 42 },
                  { "type": "KEYWORD", "value": "BOTH OF", "line": 43 },
                  { "type": "KEYWORD", "value": "WIN", "line": 43 },
                  { "type": "KEYWORD", "value": "AN", "line": 43 },
                  { "type": "KEYWORD", "value": "FAIL", "line": 43 },
                  { "type": "NEWLINE", "value": "\\n", "line": 43 },
                  { "type": "KEYWORD", "value": "EITHER OF", "line": 44 },
                  { "type": "KEYWORD", "value": "WIN", "line": 44 },
                  { "type": "KEYWORD", "value": "AN", "line": 44 },
                  { "type": "KEYWORD", "value": "FAIL", "line": 44 },
                  { "type": "NEWLINE", "value": "\\n", "line": 44 },
                  { "type": "KEYWORD", "value": "WON OF", "line": 45 },
                  { "type": "KEYWORD", "value": "WIN", "line": 45 },
                  { "type": "KEYWORD", "value": "AN", "line": 45 },
                  { "type": "KEYWORD", "value": "FAIL", "line": 45 },
                  { "type": "NEWLINE", "value": "\\n", "line": 45 },
                  { "type": "KEYWORD", "value": "NOT", "line": 46 },
                  { "type": "KEYWORD", "value": "WIN", "line": 46 },
                  { "type": "NEWLINE", "value": "\\n", "line": 46 },
                  { "type": "KEYWORD", "value": "ALL OF", "line": 47 },
                  { "type": "KEYWORD", "value": "WIN", "line": 47 },
                  { "type": "KEYWORD", "value": "AN", "line": 47 },
                  { "type": "KEYWORD", "value": "FAIL", "line": 47 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 47 },
                  { "type": "NEWLINE", "value": "\\n", "line": 47 },
                  { "type": "KEYWORD", "value": "ANY OF", "line": 48 },
                  { "type": "KEYWORD", "value": "WIN", "line": 48 },
                  { "type": "KEYWORD", "value": "AN", "line": 48 },
                  { "type": "KEYWORD", "value": "FAIL", "line": 48 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 48 },
                  { "type": "NEWLINE", "value": "\\n", "line": 48 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 49 },
                  { "type": "NUMBER", "value": 3, "line": 49 },
                  { "type": "KEYWORD", "value": "AN", "line": 49 },
                  { "type": "NUMBER", "value": 3, "line": 49 },
                  { "type": "NEWLINE", "value": "\\n", "line": 49 },
                  { "type": "KEYWORD", "value": "DIFFRINT", "line": 50 },
                  { "type": "NUMBER", "value": 3, "line": 50 },
                  { "type": "KEYWORD", "value": "AN", "line": 50 },
                  { "type": "NUMBER", "value": 4, "line": 50 },
                  { "type": "NEWLINE", "value": "\\n", "line": 50 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 51 },
                  { "type": "IDENTIFIER", "value": "NUM1", "line": 51 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 51 },
                  { "type": "NUMBER", "value": 10, "line": 51 },
                  { "type": "NEWLINE", "value": "\\n", "line": 51 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 52 },
                  { "type": "IDENTIFIER", "value": "NUM2", "line": 52 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 52 },
                  { "type": "NUMBER", "value": 20, "line": 52 },
                  { "type": "NEWLINE", "value": "\\n", "line": 52 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 53 },
                  { "type": "IDENTIFIER", "value": "BOOL1", "line": 53 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 53 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 53 },
                  { "type": "IDENTIFIER", "value": "NUM1", "line": 53 },
                  { "type": "KEYWORD", "value": "AN", "line": 53 },
                  { "type": "NUMBER", "value": 10, "line": 53 },
                  { "type": "NEWLINE", "value": "\\n", "line": 53 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 54 },
                  { "type": "IDENTIFIER", "value": "BOOL2", "line": 54 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 54 },
                  { "type": "KEYWORD", "value": "DIFFRINT", "line": 54 },
                  { "type": "IDENTIFIER", "value": "NUM2", "line": 54 },
                  { "type": "KEYWORD", "value": "AN", "line": 54 },
                  { "type": "NUMBER", "value": 20, "line": 54 },
                  { "type": "NEWLINE", "value": "\\n", "line": 54 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 55 },
                  { "type": "IDENTIFIER", "value": "RESULT1", "line": 55 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 55 },
                  { "type": "KEYWORD", "value": "BOTH OF", "line": 55 },
                  { "type": "IDENTIFIER", "value": "BOOL1", "line": 55 },
                  { "type": "KEYWORD", "value": "AN", "line": 55 },
                  { "type": "IDENTIFIER", "value": "BOOL2", "line": 55 },
                  { "type": "NEWLINE", "value": "\\n", "line": 55 },
                  { "type": "KEYWORD", "value": "KTHXBYE", "line": 56 },
                  { "type": "NEWLINE", "value": "\\n", "line": 56 }
                ]
                """
        );
    }

    @Test
    void lexStatements() throws JsonProcessingException {
        givenSourceCode("""
                HAI 1.2
                
                BTW Example: без else if
                O RLY?
                  YA RLY
                    VISIBLE "It is true!"
                  NO WAI
                    VISIBLE "It is false!"
                OIC
                                
                BTW Example: з else if
                I HAS A NUM0 ITZ 15
                BOTH SAEM NUM0 AN 10
                O RLY?
                  YA RLY
                    VISIBLE "NUM is 10"
                  MEBBE BOTH SAEM NUM0 AN 15
                    VISIBLE "NUM is 15"
                  NO WAI
                    VISIBLE "NUM is something else"
                OIC
                                
                I HAS A STR0 ITZ "R"
                STR0
                WTF?
                  OMG "R"
                    VISIBLE "RED FISH"
                    GTFO
                  OMG "Y"
                    VISIBLE "YELLOW FISH"
                  OMGWTF
                    VISIBLE "FISH IS TRANSPARENT"
                OIC
                
                KTHXBYE 
                """);

        whenLex();

        thenTokensAre("""
                [
                  { "type": "KEYWORD", "value": "HAI", "line": 1 },
                  { "type": "NUMBER", "value": "1.2", "line": 1 },
                  { "type": "NEWLINE", "value": "\\n", "line": 1 },
                  { "type": "KEYWORD", "value": "O RLY?", "line": 2 },
                  { "type": "NEWLINE", "value": "\\n", "line": 2 },
                  { "type": "KEYWORD", "value": "YA RLY", "line": 3 },
                  { "type": "NEWLINE", "value": "\\n", "line": 3 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 4 },
                  { "type": "STRING", "value": "\\"It is true!\\"", "line": 4 },
                  { "type": "NEWLINE", "value": "\\n", "line": 4 },
                  { "type": "KEYWORD", "value": "NO WAI", "line": 5 },
                  { "type": "NEWLINE", "value": "\\n", "line": 5 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 6 },
                  { "type": "STRING", "value": "\\"It is false!\\"", "line": 6 },
                  { "type": "NEWLINE", "value": "\\n", "line": 6 },
                  { "type": "KEYWORD", "value": "OIC", "line": 7 },
                  { "type": "NEWLINE", "value": "\\n", "line": 7 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 8 },
                  { "type": "IDENTIFIER", "value": "NUM0", "line": 8 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 8 },
                  { "type": "NUMBER", "value": 15, "line": 8 },
                  { "type": "NEWLINE", "value": "\\n", "line": 8 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 9 },
                  { "type": "IDENTIFIER", "value": "NUM0", "line": 9 },
                  { "type": "KEYWORD", "value": "AN", "line": 9 },
                  { "type": "NUMBER", "value": 10, "line": 9 },
                  { "type": "NEWLINE", "value": "\\n", "line": 9 },
                  { "type": "KEYWORD", "value": "O RLY?", "line": 10 },
                  { "type": "NEWLINE", "value": "\\n", "line": 10 },
                  { "type": "KEYWORD", "value": "YA RLY", "line": 11 },
                  { "type": "NEWLINE", "value": "\\n", "line": 11 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 12 },
                  { "type": "STRING", "value": "\\"NUM is 10\\"", "line": 12 },
                  { "type": "NEWLINE", "value": "\\n", "line": 12 },
                  { "type": "KEYWORD", "value": "MEBBE", "line": 13 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 13 },
                  { "type": "IDENTIFIER", "value": "NUM0", "line": 13 },
                  { "type": "KEYWORD", "value": "AN", "line": 13 },
                  { "type": "NUMBER", "value": 15, "line": 13 },
                  { "type": "NEWLINE", "value": "\\n", "line": 13 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 14 },
                  { "type": "STRING", "value": "\\"NUM is 15\\"", "line": 14 },
                  { "type": "NEWLINE", "value": "\\n", "line": 14 },
                  { "type": "KEYWORD", "value": "NO WAI", "line": 15 },
                  { "type": "NEWLINE", "value": "\\n", "line": 15 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 16 },
                  { "type": "STRING", "value": "\\"NUM is something else\\"", "line": 16 },
                  { "type": "NEWLINE", "value": "\\n", "line": 16 },
                  { "type": "KEYWORD", "value": "OIC", "line": 17 },
                  { "type": "NEWLINE", "value": "\\n", "line": 17 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 18 },
                  { "type": "IDENTIFIER", "value": "STR0", "line": 18 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 18 },
                  { "type": "STRING", "value": "\\"R\\"", "line": 18 },
                  { "type": "NEWLINE", "value": "\\n", "line": 18 },
                  { "type": "IDENTIFIER", "value": "STR0", "line": 19 },
                  { "type": "NEWLINE", "value": "\\n", "line": 19 },
                  { "type": "KEYWORD", "value": "WTF?", "line": 20 },
                  { "type": "NEWLINE", "value": "\\n", "line": 20 },
                  { "type": "KEYWORD", "value": "OMG", "line": 21 },
                  { "type": "STRING", "value": "\\"R\\"", "line": 21 },
                  { "type": "NEWLINE", "value": "\\n", "line": 21 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 22 },
                  { "type": "STRING", "value": "\\"RED FISH\\"", "line": 22 },
                  { "type": "NEWLINE", "value": "\\n", "line": 22 },
                  { "type": "KEYWORD", "value": "GTFO", "line": 23 },
                  { "type": "NEWLINE", "value": "\\n", "line": 23 },
                  { "type": "KEYWORD", "value": "OMG", "line": 24 },
                  { "type": "STRING", "value": "\\"Y\\"", "line": 24 },
                  { "type": "NEWLINE", "value": "\\n", "line": 24 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 25 },
                  { "type": "STRING", "value": "\\"YELLOW FISH\\"", "line": 25 },
                  { "type": "NEWLINE", "value": "\\n", "line": 25 },
                  { "type": "KEYWORD", "value": "OMGWTF", "line": 26 },
                  { "type": "NEWLINE", "value": "\\n", "line": 26 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 27 },
                  { "type": "STRING", "value": "\\"FISH IS TRANSPARENT\\"", "line": 27 },
                  { "type": "NEWLINE", "value": "\\n", "line": 27 },
                  { "type": "KEYWORD", "value": "OIC", "line": 28 },
                  { "type": "NEWLINE", "value": "\\n", "line": 28 },
                  { "type": "KEYWORD", "value": "KTHXBYE", "line": 29 },
                  { "type": "NEWLINE", "value": "\\n", "line": 29 }
                ]
                """
        );
    }

    @Test
    void lexLoopStatements() throws JsonProcessingException {
        givenSourceCode("""
                HAI 1.2
                
                IM IN YR LOOP
                  VISIBLE "Looping..."
                  GTFO
                IM OUTTA YR LOOP
                                
                IM IN YR LOOP UPPIN YR VAR TIL BOTH SAEM VAR AN 10
                  VISIBLE VAR
                IM OUTTA YR LOOP
                                
                BTW Цикл в циклі
                I HAS A COUNT ITZ 0
                IM IN YR OUTERLOOP
                  IM IN YR INNERLOOP
                    VISIBLE "Outer loop count: " AN COUNT
                    COUNT R SUM OF COUNT AN 1
                    BOTH SAEM COUNT AN 3, GTFO
                  IM OUTTA YR INNERLOOP
                  VISIBLE "End of inner loop"
                  GTFO
                IM OUTTA YR OUTERLOOP
                
                KTHXBYE
                """);

        whenLex();

        thenTokensAre("""
                [
                  { "type": "KEYWORD", "value": "HAI", "line": 1 },
                  { "type": "NUMBER", "value": 1.2, "line": 1 },
                  { "type": "NEWLINE", "value": "\\n", "line": 1 },
                  { "type": "KEYWORD", "value": "IM IN YR", "line": 2 },
                  { "type": "IDENTIFIER", "value": "LOOP", "line": 2 },
                  { "type": "NEWLINE", "value": "\\n", "line": 2 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 3 },
                  { "type": "STRING", "value": "\\"Looping...\\"", "line": 3 },
                  { "type": "NEWLINE", "value": "\\n", "line": 3 },
                  { "type": "KEYWORD", "value": "GTFO", "line": 4 },
                  { "type": "NEWLINE", "value": "\\n", "line": 4 },
                  { "type": "KEYWORD", "value": "IM OUTTA YR", "line": 5 },
                  { "type": "IDENTIFIER", "value": "LOOP", "line": 5 },
                  { "type": "NEWLINE", "value": "\\n", "line": 5 },
                  { "type": "KEYWORD", "value": "IM IN YR", "line": 6 },
                  { "type": "IDENTIFIER", "value": "LOOP", "line": 6 },
                  { "type": "KEYWORD", "value": "UPPIN", "line": 6 },
                  { "type": "KEYWORD", "value": "YR", "line": 6 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 6 },
                  { "type": "KEYWORD", "value": "TIL", "line": 6 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 6 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 6 },
                  { "type": "KEYWORD", "value": "AN", "line": 6 },
                  { "type": "NUMBER", "value": 10, "line": 6 },
                  { "type": "NEWLINE", "value": "\\n", "line": 6 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 7 },
                  { "type": "IDENTIFIER", "value": "VAR", "line": 7 },
                  { "type": "NEWLINE", "value": "\\n", "line": 7 },
                  { "type": "KEYWORD", "value": "IM OUTTA YR", "line": 8 },
                  { "type": "IDENTIFIER", "value": "LOOP", "line": 8 },
                  { "type": "NEWLINE", "value": "\\n", "line": 8 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 9 },
                  { "type": "IDENTIFIER", "value": "COUNT", "line": 9 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 9 },
                  { "type": "NUMBER", "value": 0, "line": 9 },
                  { "type": "NEWLINE", "value": "\\n", "line": 9 },
                  { "type": "KEYWORD", "value": "IM IN YR", "line": 10 },
                  { "type": "IDENTIFIER", "value": "OUTERLOOP", "line": 10 },
                  { "type": "NEWLINE", "value": "\\n", "line": 10 },
                  { "type": "KEYWORD", "value": "IM IN YR", "line": 11 },
                  { "type": "IDENTIFIER", "value": "INNERLOOP", "line": 11 },
                  { "type": "NEWLINE", "value": "\\n", "line": 11 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 12 },
                  { "type": "STRING", "value": "\\"Outer loop count: \\"", "line": 12 },
                  { "type": "KEYWORD", "value": "AN", "line": 12 },
                  { "type": "IDENTIFIER", "value": "COUNT", "line": 12 },
                  { "type": "NEWLINE", "value": "\\n", "line": 12 },
                  { "type": "IDENTIFIER", "value": "COUNT", "line": 13 },
                  { "type": "KEYWORD", "value": "R", "line": 13 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 13 },
                  { "type": "IDENTIFIER", "value": "COUNT", "line": 13 },
                  { "type": "KEYWORD", "value": "AN", "line": 13 },
                  { "type": "NUMBER", "value": 1, "line": 13 },
                  { "type": "NEWLINE", "value": "\\n", "line": 13 },
                  { "type": "KEYWORD", "value": "BOTH SAEM", "line": 14 },
                  { "type": "IDENTIFIER", "value": "COUNT", "line": 14 },
                  { "type": "KEYWORD", "value": "AN", "line": 14 },
                  { "type": "NUMBER", "value": 3, "line": 14 },
                  { "type": "KEYWORD", "value": "GTFO", "line": 14 },
                  { "type": "NEWLINE", "value": "\\n", "line": 14 },
                  { "type": "KEYWORD", "value": "IM OUTTA YR", "line": 15 },
                  { "type": "IDENTIFIER", "value": "INNERLOOP", "line": 15 },
                  { "type": "NEWLINE", "value": "\\n", "line": 15 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 16 },
                  { "type": "STRING", "value": "\\"End of inner loop\\"", "line": 16 },
                  { "type": "NEWLINE", "value": "\\n", "line": 16 },
                  { "type": "KEYWORD", "value": "GTFO", "line": 17 },
                  { "type": "NEWLINE", "value": "\\n", "line": 17 },
                  { "type": "KEYWORD", "value": "IM OUTTA YR", "line": 18 },
                  { "type": "IDENTIFIER", "value": "OUTERLOOP", "line": 18 },
                  { "type": "NEWLINE", "value": "\\n", "line": 18 },
                  { "type": "KEYWORD", "value": "KTHXBYE", "line": 19 },
                  { "type": "NEWLINE", "value": "\\n", "line": 19 }
                ]
                """);
    }

    @Test
    void lexFunctions() throws JsonProcessingException {
        givenSourceCode("""
                HAI 1.2
                
                BTW Функція з двома аргументами
                HOW IZ I FUNC YR ARG1 AN YR ARG2
                  VISIBLE ARG1
                  VISIBLE ARG2
                IF U SAY SO
                                
                I IZ FUNC YR "Hello" AN YR "World" MKAY
                                
                BTW Функція з поверненням значення
                HOW IZ I ADD YR A1 AN YR B
                  FOUND YR SUM OF A1 AN B
                IF U SAY SO
                                
                I HAS A RESULT ITZ I IZ ADD YR 5 AN YR 10 MKAY
                VISIBLE RESULT
                                
                HOW IZ I ADD1 YR A1 AN YR B
                  I HAS A RESULT ITZ SUM OF A1 AN B
                  FOUND YR RESULT
                IF U SAY SO
                                
                I HAS A RESULT0 ITZ I IZ ADD1 YR 5 AN YR 10 MKAY
                VISIBLE RESULT0
                                
                BTW Вкладені функції
                HOW IZ I FUNC2 YR ARG2
                  VISIBLE ARG2
                IF U SAY SO
                                
                HOW IZ I FUNC1 YR ARG1
                  VISIBLE ARG1
                  I IZ FUNC2 YR "Inner call" MKAY
                IF U SAY SO
                                
                I IZ FUNC1 YR "Outer call" MKAY
                
                KTHXBYE
                """);

        whenLex();

        thenTokensAre("""
                [
                  { "type": "KEYWORD", "value": "HAI", "line": 1 },
                  { "type": "NUMBER", "value": 1.2, "line": 1 },
                  { "type": "NEWLINE", "value": "\\n", "line": 1 },
                  { "type": "KEYWORD", "value": "HOW IZ I", "line": 2 },
                  { "type": "IDENTIFIER", "value": "FUNC", "line": 2 },
                  { "type": "KEYWORD", "value": "YR", "line": 2 },
                  { "type": "IDENTIFIER", "value": "ARG1", "line": 2 },
                  { "type": "KEYWORD", "value": "AN", "line": 2 },
                  { "type": "KEYWORD", "value": "YR", "line": 2 },
                  { "type": "IDENTIFIER", "value": "ARG2", "line": 2 },
                  { "type": "NEWLINE", "value": "\\n", "line": 2 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 3 },
                  { "type": "IDENTIFIER", "value": "ARG1", "line": 3 },
                  { "type": "NEWLINE", "value": "\\n", "line": 3 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 4 },
                  { "type": "IDENTIFIER", "value": "ARG2", "line": 4 },
                  { "type": "NEWLINE", "value": "\\n", "line": 4 },
                  { "type": "KEYWORD", "value": "IF U SAY SO", "line": 5 },
                  { "type": "NEWLINE", "value": "\\n", "line": 5 },
                  { "type": "KEYWORD", "value": "I IZ", "line": 6 },
                  { "type": "IDENTIFIER", "value": "FUNC", "line": 6 },
                  { "type": "KEYWORD", "value": "YR", "line": 6 },
                  { "type": "STRING", "value": "\\"Hello\\"", "line": 6 },
                  { "type": "KEYWORD", "value": "AN", "line": 6 },
                  { "type": "KEYWORD", "value": "YR", "line": 6 },
                  { "type": "STRING", "value": "\\"World\\"", "line": 6 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 6 },
                  { "type": "NEWLINE", "value": "\\n", "line": 6 },
                  { "type": "KEYWORD", "value": "HOW IZ I", "line": 7 },
                  { "type": "IDENTIFIER", "value": "ADD", "line": 7 },
                  { "type": "KEYWORD", "value": "YR", "line": 7 },
                  { "type": "IDENTIFIER", "value": "A1", "line": 7 },
                  { "type": "KEYWORD", "value": "AN", "line": 7 },
                  { "type": "KEYWORD", "value": "YR", "line": 7 },
                  { "type": "IDENTIFIER", "value": "B", "line": 7 },
                  { "type": "NEWLINE", "value": "\\n", "line": 7 },
                  { "type": "KEYWORD", "value": "FOUND YR", "line": 8 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 8 },
                  { "type": "IDENTIFIER", "value": "A1", "line": 8 },
                  { "type": "KEYWORD", "value": "AN", "line": 8 },
                  { "type": "IDENTIFIER", "value": "B", "line": 8 },
                  { "type": "NEWLINE", "value": "\\n", "line": 8 },
                  { "type": "KEYWORD", "value": "IF U SAY SO", "line": 9 },
                  { "type": "NEWLINE", "value": "\\n", "line": 9 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 10 },
                  { "type": "IDENTIFIER", "value": "RESULT", "line": 10 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 10 },
                  { "type": "KEYWORD", "value": "I IZ", "line": 10 },
                  { "type": "IDENTIFIER", "value": "ADD", "line": 10 },
                  { "type": "KEYWORD", "value": "YR", "line": 10 },
                  { "type": "NUMBER", "value": 5, "line": 10 },
                  { "type": "KEYWORD", "value": "AN", "line": 10 },
                  { "type": "KEYWORD", "value": "YR", "line": 10 },
                  { "type": "NUMBER", "value": 10, "line": 10 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 10 },
                  { "type": "NEWLINE", "value": "\\n", "line": 10 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 11 },
                  { "type": "IDENTIFIER", "value": "RESULT", "line": 11 },
                  { "type": "NEWLINE", "value": "\\n", "line": 11 },
                  { "type": "KEYWORD", "value": "HOW IZ I", "line": 12 },
                  { "type": "IDENTIFIER", "value": "ADD1", "line": 12 },
                  { "type": "KEYWORD", "value": "YR", "line": 12 },
                  { "type": "IDENTIFIER", "value": "A1", "line": 12 },
                  { "type": "KEYWORD", "value": "AN", "line": 12 },
                  { "type": "KEYWORD", "value": "YR", "line": 12 },
                  { "type": "IDENTIFIER", "value": "B", "line": 12 },
                  { "type": "NEWLINE", "value": "\\n", "line": 12 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 13 },
                  { "type": "IDENTIFIER", "value": "RESULT", "line": 13 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 13 },
                  { "type": "KEYWORD", "value": "SUM OF", "line": 13 },
                  { "type": "IDENTIFIER", "value": "A1", "line": 13 },
                  { "type": "KEYWORD", "value": "AN", "line": 13 },
                  { "type": "IDENTIFIER", "value": "B", "line": 13 },
                  { "type": "NEWLINE", "value": "\\n", "line": 13 },
                  { "type": "KEYWORD", "value": "FOUND YR", "line": 14 },
                  { "type": "IDENTIFIER", "value": "RESULT", "line": 14 },
                  { "type": "NEWLINE", "value": "\\n", "line": 14 },
                  { "type": "KEYWORD", "value": "IF U SAY SO", "line": 15 },
                  { "type": "NEWLINE", "value": "\\n", "line": 15 },
                  { "type": "KEYWORD", "value": "I HAS A", "line": 16 },
                  { "type": "IDENTIFIER", "value": "RESULT0", "line": 16 },
                  { "type": "KEYWORD", "value": "ITZ", "line": 16 },
                  { "type": "KEYWORD", "value": "I IZ", "line": 16 },
                  { "type": "IDENTIFIER", "value": "ADD1", "line": 16 },
                  { "type": "KEYWORD", "value": "YR", "line": 16 },
                  { "type": "NUMBER", "value": 5, "line": 16 },
                  { "type": "KEYWORD", "value": "AN", "line": 16 },
                  { "type": "KEYWORD", "value": "YR", "line": 16 },
                  { "type": "NUMBER", "value": 10, "line": 16 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 16 },
                  { "type": "NEWLINE", "value": "\\n", "line": 16 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 17 },
                  { "type": "IDENTIFIER", "value": "RESULT0", "line": 17 },
                  { "type": "NEWLINE", "value": "\\n", "line": 17 },
                  { "type": "KEYWORD", "value": "HOW IZ I", "line": 18 },
                  { "type": "IDENTIFIER", "value": "FUNC2", "line": 18 },
                  { "type": "KEYWORD", "value": "YR", "line": 18 },
                  { "type": "IDENTIFIER", "value": "ARG2", "line": 18 },
                  { "type": "NEWLINE", "value": "\\n", "line": 18 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 19 },
                  { "type": "IDENTIFIER", "value": "ARG2", "line": 19 },
                  { "type": "NEWLINE", "value": "\\n", "line": 19 },
                  { "type": "KEYWORD", "value": "IF U SAY SO", "line": 20 },
                  { "type": "NEWLINE", "value": "\\n", "line": 20 },
                  { "type": "KEYWORD", "value": "HOW IZ I", "line": 21 },
                  { "type": "IDENTIFIER", "value": "FUNC1", "line": 21 },
                  { "type": "KEYWORD", "value": "YR", "line": 21 },
                  { "type": "IDENTIFIER", "value": "ARG1", "line": 21 },
                  { "type": "NEWLINE", "value": "\\n", "line": 21 },
                  { "type": "KEYWORD", "value": "VISIBLE", "line": 22 },
                  { "type": "IDENTIFIER", "value": "ARG1", "line": 22 },
                  { "type": "NEWLINE", "value": "\\n", "line": 22 },
                  { "type": "KEYWORD", "value": "I IZ", "line": 23 },
                  { "type": "IDENTIFIER", "value": "FUNC2", "line": 23 },
                  { "type": "KEYWORD", "value": "YR", "line": 23 },
                  { "type": "STRING", "value": "\\"Inner call\\"", "line": 23 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 23 },
                  { "type": "NEWLINE", "value": "\\n", "line": 23 },
                  { "type": "KEYWORD", "value": "IF U SAY SO", "line": 24 },
                  { "type": "NEWLINE", "value": "\\n", "line": 24 },
                  { "type": "KEYWORD", "value": "I IZ", "line": 25 },
                  { "type": "IDENTIFIER", "value": "FUNC1", "line": 25 },
                  { "type": "KEYWORD", "value": "YR", "line": 25 },
                  { "type": "STRING", "value": "\\"Outer call\\"", "line": 25 },
                  { "type": "KEYWORD", "value": "MKAY", "line": 25 },
                  { "type": "NEWLINE", "value": "\\n", "line": 25 },
                  { "type": "KEYWORD", "value": "KTHXBYE", "line": 26 },
                  { "type": "NEWLINE", "value": "\\n", "line": 26 }
                ]
                """);
    }

    @Test
    void lexLongComment() throws JsonProcessingException {
        givenSourceCode("""
                BTW oneline comment
                OBTW this is a long comment block
                                 see, i have more comments here
                                 and here
                TLDR
                BTW oneline comment
                """);

        whenLex();

        thenTokensAre("[]");
    }

    @Test
    void lexOneLineComment() throws JsonProcessingException {
        givenSourceCode("""
                BTW this is a one-line comment
                """);

        whenLex();

        thenTokensAre("[]");
    }

    @ParameterizedTest
    @MethodSource("illegalTokensDeclaration")
    void lexShouldThrowIfTokensAreInvalid(String code) {
        givenSourceCode(code);
        System.out.println(code);

        assertThatThrownBy(this::whenLex)
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    static Stream<String> illegalHaiKthxbueDeclaration() {
        return Stream.of("""     
                I HAS A VAR             BTW wasnt open
                """, """
                HAI 1.2                 BTW wasnt closed
                
                I HAS A VAR
                """, """
                I HAS A VAR             BTW wasnt open
                
                KTHXBYE
                """
        );
    }

    static Stream<String> illegalTokensDeclaration() {
        return Stream.of(
                "HAI",
                "HAI ",
                "HAI 1.3",

                "I HAS A",
                "I HAS A ",
                "I HAS A 123VAR",

                "ITZ",
                "I HAS A VARNAME ITZ",
                "I HAS A ITZ",

                "GIMMEH",
                "GIMMEH ",

                " any tokens GTFO",

                "IM IN YR",
                "var IM IN YR LOOPNAME",

                """
                        IM IN YR OUTERLOOP
                            BTW unclosed loop
                        IM OUTTA YR OUTERLOOP2
                        """,

                """
                        IM IN YR
                            BTW without name
                        """,
                """
                        IM OUTTA YR OUTERLOOP2
                        """,

                "VISIBLE",

                "SUM OF 1"
        );
    }


    private void givenSourceCode(String code) {
        sourceCode = new TestSourceCode(code);
    }

    private void whenLex() {
        tokens = lexer.lex(sourceCode);
    }

    private void thenTokensAre(String json) throws JsonProcessingException {
        Tokens expected = mapper.readValue(json, Tokens.class);
        assertThat(tokens).containsExactlyElementsOf(expected);
    }
}
