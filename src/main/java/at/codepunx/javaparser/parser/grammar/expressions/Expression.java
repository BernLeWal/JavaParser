package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class Expression extends AssignmentExpression {
    /*
        <expression> ::= <assignment expression>
     */
    public Expression(TokenReader<JavaTokenType> reader) throws ParseException {
        super(reader);
    }
}
