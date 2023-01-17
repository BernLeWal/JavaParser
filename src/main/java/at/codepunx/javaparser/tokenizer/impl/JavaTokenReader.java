package at.codepunx.javaparser.tokenizer.impl;

import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.TokenReader;

import java.util.List;

public class JavaTokenReader extends TokenReader<JavaTokenType> implements Cloneable {
    public JavaTokenReader(List<Token<JavaTokenType>> tokens) {
        super(tokens);
    }

    public JavaTokenReader(TokenReader<JavaTokenType> that) {
        super(that);
    }

    @Override
    public Object clone() {
        super.clone();
        return new JavaTokenReader(this);
    }
}
