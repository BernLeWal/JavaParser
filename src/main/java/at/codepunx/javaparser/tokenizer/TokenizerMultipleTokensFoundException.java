package at.codepunx.javaparser.tokenizer;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class TokenizerMultipleTokensFoundException extends TokenizerException {
    @Getter
    private final String currentText;
    @Getter
    private final List<JavaTokenType> possibleTokens;

    public TokenizerMultipleTokensFoundException(String currentText, List<JavaTokenType> possibleTokens) {
        super("Multiple tokens found for '" + currentText + "': " + possibleTokens.stream().map(JavaTokenType::toString).collect(Collectors.joining(", ")));
        this.currentText = currentText;
        this.possibleTokens = possibleTokens;
    }
}
