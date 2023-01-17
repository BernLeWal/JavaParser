package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class MultiplicativeExpression extends Node {
    /*
        <multiplicative expression> ::= <unary expression>
                                      | <multiplicative expression> '*' <unary expression>
                                      | <multiplicative expression> '/' <unary expression>
                                      | <multiplicative expression> '%' <unary expression>
     */
    public MultiplicativeExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                UnaryExpression::new,
                r->{
                    p.mandatory(  MultiplicativeExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "*").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  MultiplicativeExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "/").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  MultiplicativeExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "%").sendTo(this::setValue);
                    p.mandatory(  UnaryExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
