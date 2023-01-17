package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.PrimitiveType;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class CastExpression extends Node {
    /*
        <cast expression> ::= '(' <primitive type> ')' <unary expression>
                            | '(' <reference type> ')' <unary expression not plus minus>
     */
    public CastExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                r->{
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_OPEN);
                    p.mandatory( PrimitiveType::new ).sendTo(n->setValue(n.getValue()));
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_CLOSE);
                    p.mandatory( UnaryExpression::new).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_OPEN);
                    p.mandatory( ReferenceType::new ).sendTo(n->setValue(n.getValue()));
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_CLOSE);
                    p.mandatory( UnaryExpressionNotPlusMinus::new).sendTo(this::addChild);
                    return this;
                }
        );
    }
}
