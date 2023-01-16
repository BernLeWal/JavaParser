package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class UnaryExpressionNotPlusMinus extends Node {
    /*
        <unary expression not plus minus> ::= <postfix expression>
                                         | '~' <unary expression>
                                         | '!' <unary expression>
                                         | <cast expression>
     */
    public UnaryExpressionNotPlusMinus(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                PostfixExpression::new,
                r->{
                    mandatoryToken( r, JavaTokenType.OPERATOR, "~").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatoryToken( r, JavaTokenType.OPERATOR, "!").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                CastExpression::new
        ).sendTo(this::addChild);
    }
}
