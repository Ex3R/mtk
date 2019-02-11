package ru.nsu.fit.terekhov;

import lombok.Data;

@Data
public class Lexeme {

    private LexemeType lexemeType;
    private String lexemeText;

    public Lexeme(LexemeType lexemeType, String lexemeText) {
        this.lexemeType = lexemeType;
        this.lexemeText = lexemeText;
    }

    public enum LexemeType {
        NUMBER(""),
        OPEN_BRACE("("),
        CLOSE_BRACE(")"),
        MUL("*"),
        DIV("/"),
        POW("^"),
        PLUS("+"),
        MINUS("-"),
        UNARY_MINUS("-"),
        EOF("-1");
        private String value;

        LexemeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


    }
}
