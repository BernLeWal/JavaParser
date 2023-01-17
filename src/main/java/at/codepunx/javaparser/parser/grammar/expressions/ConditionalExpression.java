package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ConditionalExpression extends Node {
    /*
        <conditional expression> ::= <conditional or expression>
                                | <conditional or expression> '?' <expression> ':' <conditional expression>
        <expression> ::= <assignment expression>
     */
    public ConditionalExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                ConditionalOrExpression::new,
                r->{
                    p.mandatory( ConditionalOrExpression::new).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "?");
                    p.mandatory( Expression::new).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, ":");
                    p.mandatory( ConditionalExpression::new).sendTo(this::addChild);
                    return this;
                }
                ).sendTo(this::addChild);
    }
}
