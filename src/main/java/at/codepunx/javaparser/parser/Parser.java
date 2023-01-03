package at.codepunx.javaparser.parser;

import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.List;

public class Parser {
    public NodeInterface parse(List<Token<JavaTokenType>> tokens) throws ParserException {
        if ( tokens==null || tokens.isEmpty() )
            return null;

        // TODO
        return null;
    }
}
