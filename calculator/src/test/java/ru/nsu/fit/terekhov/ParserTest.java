package ru.nsu.fit.terekhov;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void simpleExpressionTest() {
        StringReader text = new StringReader("111 - 11 + 50 - 50");
        StringReader text1 = new StringReader("0 - 0 -0 + 5");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);

        try {
            Parser p = new Parser(l);
            assertEquals(100, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(5, p1.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();

        }
    }

    @Test
    void simpleTermTest() {
        StringReader text = new StringReader("15 * 4 / 2");
        StringReader text1 = new StringReader("3 + 10 * 5");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);

        try {
            Parser p = new Parser(l);
            assertEquals(30, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(53, p1.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();

        }
    }

    @Test
    void factorTest() {
        StringReader text = new StringReader("(2^(3+5^1)/5)");
        StringReader text1 = new StringReader("5^3^2");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);

        try {
            Parser p = new Parser(l);
            assertEquals(51, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(1953125, p1.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();
        }
    }

    @Test
    void braceTest() {
        StringReader text = new StringReader("15 / (3 + 2)");
        StringReader text1 = new StringReader("(17 +23) * 2");
        StringReader text2 = new StringReader("((( (((7 + 5) / 4) * 3)*(48/4 + 5) )))");
        StringReader text3 = new StringReader("(((2)");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);
        Lexer l2 = new Lexer(text2);
        Lexer l3 = new Lexer(text3);

        try {
            Parser p = new Parser(l);
            assertEquals(3, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(80, p1.calculate());

            Parser p2 = new Parser(l2);
            assertEquals(153, p2.calculate());

            Parser p3 = new Parser(l3);
            assertThrows(ParserException.class, p3::calculate);

        } catch (IOException | ParserException | LexerException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parsePowerTest() {
        StringReader text = new StringReader("-(1--9)");
        StringReader text1 = new StringReader("-(-1*(-3+9))");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);

        try {
            Parser p = new Parser(l);
            assertEquals(-10, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(6, p1.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();
        }
    }

    @Test
    void simpleAtomTest() {
        StringReader text = new StringReader("123");

        Lexer l = new Lexer(text);


        try {
            Parser p = new Parser(l);
            assertEquals(123, p.calculate());


        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();
        }
    }

    @Test
    void calculateTest() {
        StringReader text = new StringReader("((2^3+4*(2^(64/2-8))-5)+10^2^-(2-2))");
        Lexer l = new Lexer(text);

        try {
            Parser p = new Parser(l);
            assertEquals(67108877, p.calculate());

        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();
        }
    }
}