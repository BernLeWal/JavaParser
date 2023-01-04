package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.NodeInterface;
import at.codepunx.javaparser.parser.ParserException;
import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.List;

public class JavaParser {
    private final JavaGrammar grammar;

    public JavaParser(JavaGrammar grammar) {
        this.grammar = grammar;
    }

    public NodeInterface parse(List<Token<JavaTokenType>> tokens) throws ParserException {
        if ( tokens==null || tokens.isEmpty() )
            return null;

        var reader = new TokenReader<>(tokens);
        reader.setWhitespaceTokenTypes( new JavaTokenType[]{JavaTokenType.WHITESPACE} );

        try {
            return grammar.createRootNode(reader);
        } catch (TokenReaderException e) {
            throw new ParserException(e.getMessage());
        }
    }

}
