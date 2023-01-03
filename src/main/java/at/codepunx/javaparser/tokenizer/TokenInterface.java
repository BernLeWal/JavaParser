package at.codepunx.javaparser.tokenizer;

public interface TokenInterface<T extends TokenTypeInterface> {
    T getType();
    String getValue();
}
