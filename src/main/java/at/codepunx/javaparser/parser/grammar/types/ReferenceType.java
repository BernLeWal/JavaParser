package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ReferenceType extends ExpressionName {
    public ReferenceType(TokenReader<JavaTokenType> reader) throws ParseException {
        super(reader);
    }
}
