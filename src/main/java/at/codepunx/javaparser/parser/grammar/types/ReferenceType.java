package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ReferenceType extends Node {
    public ReferenceType(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            StringBuilder fqcn = new StringBuilder();
            fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
                fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
