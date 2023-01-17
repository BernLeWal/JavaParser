package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ExpressionName extends Node {
    public ExpressionName(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        try {
            StringBuilder fqcn = new StringBuilder();
            fqcn.append(p.getReader().readToken(JavaTokenType.IDENTIFIER).getValue());
            while( p.getReader().tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(p.getReader().readToken(JavaTokenType.DOT).getValue());
                fqcn.append(p.getReader().readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
