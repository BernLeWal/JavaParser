package at.codepunx.javaparser.tokenizer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JavaToken {
    private final JavaTokenType type;
    private final String value;

}
