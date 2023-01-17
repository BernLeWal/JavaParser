package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class UnaryExpressionNotPlusMinus extends Node {
    /*
        <unary expression not plus minus> ::= <postfix expression>
                                         | '~' <unary expression>
                                         | '!' <unary expression>
                                         | <cast expression>
     */
    public UnaryExpressionNotPlusMinus(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                PostfixExpression::new,
                r->{
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "~").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "!").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                CastExpression::new
        ).sendTo(this::addChild);
    }
}
