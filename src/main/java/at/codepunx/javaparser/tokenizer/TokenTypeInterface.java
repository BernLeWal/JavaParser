package at.codepunx.javaparser.tokenizer;

import java.util.Optional;

public interface TokenTypeInterface {
    String name();
    Optional<Boolean> isValid(String s);
}
