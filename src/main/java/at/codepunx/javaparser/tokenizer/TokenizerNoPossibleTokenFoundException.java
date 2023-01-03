package at.codepunx.javaparser.tokenizer;

public class TokenizerNoPossibleTokenFoundException extends TokenizerException {
    private final String currentText;

    public TokenizerNoPossibleTokenFoundException(String currentText) {
        super("No possible token found for '" + currentText + "'!");
        this.currentText = currentText;
    }
}
