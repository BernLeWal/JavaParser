package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ReferenceType extends ExpressionName {
    public ReferenceType(Parser<JavaTokenType> p) throws ParseException {
        super( p );
    }
}
