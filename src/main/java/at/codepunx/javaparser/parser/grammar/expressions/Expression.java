package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class Expression extends AssignmentExpression {
    /*
        <expression> ::= <assignment expression>
     */
    public Expression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
    }
}
