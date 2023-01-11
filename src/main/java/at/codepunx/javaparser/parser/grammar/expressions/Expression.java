package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class Expression extends Node {
    public Expression(TokenReader<JavaTokenType> reader) throws ParseException {
        // TODO
        while ( !reader.tryReadToken(JavaTokenType.SEMIKOLON) ) {
            System.out.println("Expression: skipped token " + reader.next().getValue());
        }
    }
}
