package at.codepunx.javaparser.tokenizer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JavaToken {
    @Getter
    private final JavaTokenType type;
    @Getter
    private final String value;

    @Override
    public String toString() {
        return String.format("%s %s ", type.name(), value);
    }
}
