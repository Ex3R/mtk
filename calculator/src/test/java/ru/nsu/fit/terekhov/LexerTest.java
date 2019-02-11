package ru.nsu.fit.terekhov;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LexerTest {

    @Test
    void shouldReturnStringWithoutSpaces() throws IOException {
        String inputStr = "2   +    8      ";
        String outputStr;
        StringReader reader = new StringReader(inputStr);

        Lexer lexer = new Lexer(reader);


        Lexeme lexeme1 = lexer.getLexeme();
        assertEquals(Lexeme.LexemeType.NUMBER, lexeme1.getLexemeType());
        assertEquals("2", lexeme1.getLexemeText());
        outputStr = lexeme1.getLexemeText();

        Lexeme lexeme2 = lexer.getLexeme();
        assertEquals(Lexeme.LexemeType.PLUS, lexeme2.getLexemeType());
        assertEquals("+", lexeme2.getLexemeText());
        outputStr += lexeme2.getLexemeText();

        Lexeme lexeme3 = lexer.getLexeme();
        assertEquals(Lexeme.LexemeType.NUMBER, lexeme3.getLexemeType());
        assertEquals("8", lexeme3.getLexemeText());
        outputStr += lexeme3.getLexemeText();

        Lexeme lexeme4 = lexer.getLexeme();
        assertEquals(Lexeme.LexemeType.EOF, lexeme4.getLexemeType());
        assertEquals("", lexeme4.getLexemeText());

        assertEquals(outputStr, "2+8");

    }

    @Test
    void lexemesTest() throws IOException {
        String inputStr = "( ) + - * / 111-11";
        StringReader reader = new StringReader(inputStr);

        Lexer l = new Lexer(reader);

        Lexeme lexeme = l.getLexeme();
        assertEquals(Lexeme.LexemeType.OPEN_BRACE, lexeme.getLexemeType());
        assertEquals("(", lexeme.getLexemeText());

        Lexeme lexeme1 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.CLOSE_BRACE, lexeme1.getLexemeType());
        assertEquals(")", lexeme1.getLexemeText());

        Lexeme lexeme2 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.PLUS, lexeme2.getLexemeType());
        assertEquals("+", lexeme2.getLexemeText());

        Lexeme lexeme3 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.UNARY_MINUS, lexeme3.getLexemeType());
        assertEquals("-", lexeme3.getLexemeText());

        Lexeme lexeme4 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.MUL, lexeme4.getLexemeType());
        assertEquals("*", lexeme4.getLexemeText());

        Lexeme lexeme5 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.DIV, lexeme5.getLexemeType());
        assertEquals("/", lexeme5.getLexemeText());

        Lexeme lexeme6 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.NUMBER, lexeme6.getLexemeType());
        assertEquals("111", lexeme6.getLexemeText());

        Lexeme lexeme7 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.MINUS, lexeme7.getLexemeType());
        assertEquals("-", lexeme7.getLexemeText());

        Lexeme lexeme8 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.NUMBER, lexeme8.getLexemeType());
        assertEquals("11", lexeme8.getLexemeText());

        Lexeme lexeme9 = l.getLexeme();
        assertEquals(Lexeme.LexemeType.EOF, lexeme9.getLexemeType());
        assertEquals("", lexeme9.getLexemeText());

    }

    @Test
    void shouldThrowExceptionFiveTimes() throws IOException {
        String inputStr = "a b c d";
        StringReader reader = new StringReader(inputStr);

        Lexer l = new Lexer(reader);

        for (int i = 0; i < 4; i++) {
            assertThrows(LexerException.class, l::getLexeme);
        }
    }
}