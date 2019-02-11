package ru.nsu.fit.terekhov;

import ru.nsu.fit.terekhov.Lexeme.LexemeType;

import java.io.IOException;

import static ru.nsu.fit.terekhov.Lexeme.LexemeType.MINUS;

public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int calculate() throws IOException, ParserException, LexerException {
        getNextLexeme();
        return parseExpression();
    }

    private int parseExpression() throws IOException, ParserException, LexerException {

        int expressionSum = parseTerm();

        while (currentLexeme.getLexemeType() == LexemeType.PLUS || currentLexeme.getLexemeType() == MINUS) {
            int sign = defineSign();
            getNextLexeme();
            expressionSum += sign * parseTerm();
        }
        return expressionSum;
    }

    private int parseTerm() throws IOException, ParserException, LexerException {
        int termSum = parseFactor();

        while (currentLexeme.getLexemeType() == LexemeType.DIV || currentLexeme.getLexemeType() == LexemeType.MUL) {
            if (currentLexeme.getLexemeType() == LexemeType.DIV) {
                getNextLexeme();
                termSum /= parseFactor();
            } else {
                getNextLexeme();
                termSum *= parseFactor();
            }
        }
        return termSum;
    }

    private int parseFactor() throws IOException, ParserException, LexerException {
        int factorSum = parsePower();

        while (currentLexeme.getLexemeType() == LexemeType.POW) {
            getNextLexeme();
            factorSum = (int) Math.pow(factorSum, parseFactor());
        }

        return factorSum;
    }

    private int parsePower() throws IOException, ParserException, LexerException {
        int powerSum;
        if (currentLexeme.getLexemeType() == LexemeType.UNARY_MINUS) {
            getNextLexeme();
            powerSum = -1 * parseAtom();
        } else {
            powerSum = parseAtom();
        }
        return powerSum;
    }

    private int parseAtom() throws IOException, ParserException, LexerException {
        switch (currentLexeme.getLexemeType()) {
            case NUMBER: {
                int temp = Integer.parseInt(currentLexeme.getLexemeText());
                getNextLexeme();
                return temp;
            }
            case OPEN_BRACE: {
                getNextLexeme();
                int temp = parseExpression();
                if (currentLexeme.getLexemeType() == Lexeme.LexemeType.CLOSE_BRACE) {
                    getNextLexeme();
                    return temp;
                } else {
                    throw new ParserException("no closing brace");
                }
            }
            default: {
                throw new ParserException("incorrect lexeme");
            }
        }
    }

    private int defineSign() throws IOException, ParserException, LexerException {
        int sign;
        switch (currentLexeme.getLexemeType()) {
            case MINUS: {
                sign = -1;
                break;
            }
            case PLUS: {
                sign = 1;
                break;
            }
            default: {
                throw new ParserException("incorrect sign");
            }
        }
        return sign;
    }

    private void getNextLexeme() throws IOException, LexerException {
        currentLexeme = lexer.getLexeme();
    }
}

