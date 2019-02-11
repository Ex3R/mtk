package ru.nsu.fit.terekhov;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

public class Lexer {
    private static final Logger log = Logger.getLogger(Lexer.class.getName());
    private static final int EOF = -1;
    private static final String WHITESPACE = " ";
    private Reader reader;
    private Lexeme lastReadLexeme;
    private StringBuilder currentLexeme;

    public Lexer(Reader reader) {
        currentLexeme = new StringBuilder();
        this.reader = reader;
    }


    public Lexeme getLexeme() throws IOException, LexerException {
        Lexeme.LexemeType lexemeType = null;
        currentLexeme.setLength(0);

        try {
            readNext();

            skipWhitespaces();

            if (isCurrentLexemeNubmer()) {
                while (isNextCharNumber()) {
                    reader.reset();
                    readNext();
                }
                reader.reset();
                lexemeType = Lexeme.LexemeType.NUMBER;
            } else if (Lexeme.LexemeType.CLOSE_BRACE.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.CLOSE_BRACE;
            } else if (Lexeme.LexemeType.OPEN_BRACE.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.OPEN_BRACE;
            } else if (Lexeme.LexemeType.MUL.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.MUL;
            } else if (Lexeme.LexemeType.DIV.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.DIV;
            } else if (Lexeme.LexemeType.POW.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.POW;
            } else if (Lexeme.LexemeType.PLUS.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.PLUS;
            } else if (isCurrentLexemeBinaryMinus()) {
                lexemeType = Lexeme.LexemeType.MINUS;
            } else if (Lexeme.LexemeType.UNARY_MINUS.getValue().equals(currentLexeme.toString())) {
                lexemeType = Lexeme.LexemeType.UNARY_MINUS;
            }
        } catch (IOException | LexerException e) {
            log.info("eof found");
            lastReadLexeme = new Lexeme(Lexeme.LexemeType.EOF, "");
            return lastReadLexeme;
        }

        if (lexemeType == null) {
            throw new LexerException("unknown lexeme");
        }

        log.info(format("read currentLexeme: {0} type: {1} ", currentLexeme.toString(), lexemeType.name()));
        lastReadLexeme = new Lexeme(lexemeType, currentLexeme.toString());
        return lastReadLexeme;


    }

    private void readNext() throws IOException {
        char[] buffer = new char[1];
        int read = reader.read(buffer);

        if (read == EOF) {
            throw new LexerException("EOF found, need to stop");
        }

        currentLexeme.append(buffer);
    }

    private void skipWhitespaces() throws IOException, LexerException {
        while (currentLexeme.toString().replaceAll("\\s+", " ").equals(WHITESPACE)) {
            currentLexeme.delete(0, 1);
            readNext();
        }
    }

    private boolean isCurrentLexemeNubmer() {
        return Pattern.matches("[0-9]", currentLexeme.toString());
    }

    private boolean isNextCharNumber() throws IOException {
        char[] buf = new char[1];
        reader.mark(1);
        int read = reader.read(buf);
        return (read != EOF) && (new String(buf).matches("[0-9]"));
    }

    private boolean isCurrentLexemeBinaryMinus() {
        return currentLexeme.toString().equals(Lexeme.LexemeType.MINUS.getValue()) &&
                lastReadLexeme != null &&
                (lastReadLexeme.getLexemeType() == Lexeme.LexemeType.NUMBER ||
                        lastReadLexeme.getLexemeType() == Lexeme.LexemeType.CLOSE_BRACE);
    }

}
