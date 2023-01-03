package at.codepunx.javaparser.tokenizer;

import lombok.Getter;

public class Token<T extends TokenTypeInterface> implements TokenInterface<T> {
    @Getter
    private final T type;
    @Getter
    private final String value;

    public Token(T type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s %s ", type.name(), value);
    }

}
