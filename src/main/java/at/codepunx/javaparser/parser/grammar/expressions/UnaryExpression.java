package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class UnaryExpression extends Node {
    /*
        <unary expression> ::= <preincrement expression>
                            | <predecrement expression>
                            | '+' <unary expression>
                            | '-' <unary expression>
                            | <unary expression not plus minus>
     */
    public UnaryExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                PreincrementExpression::new,
                PredecrementExpression::new,
                r->{
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "+").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "-").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                UnaryExpressionNotPlusMinus::new
        ).sendTo(this::addChild);
    }
}
