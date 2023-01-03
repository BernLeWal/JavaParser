package at.codepunx.javaparser.tokenizer;

import lombok.Getter;

public class TokenizerNoPossibleTokenFoundException extends TokenizerException {
    @Getter
    private final String currentText;

    public TokenizerNoPossibleTokenFoundException(String currentText) {
        super("No possible token found for '" + currentText + "'!");
        this.currentText = currentText;
    }
}
