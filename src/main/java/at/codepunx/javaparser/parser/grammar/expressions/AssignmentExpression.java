package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class AssignmentExpression extends Node {
    /*
        <assignment expression> ::= <conditional expression>
                                | <assignment>
     */
    public AssignmentExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(
                ConditionalExpression::new,
                Assignment::new
        ).sendTo(this::addChild);

//        while ( !reader.tryReadToken(JavaTokenType.SEMIKOLON) ) {
//            System.out.println("Expression: skipped token " + reader.next().getValue());
//        }
    }
}
