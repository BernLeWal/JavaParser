package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class AdditiveExpression extends Node {
    /*
        <additive expression> ::= <multiplicative expression>
                                | <additive expression> '+' <multiplicative expression>
                                | <additive expression> '-' <multiplicative expression>
     */
    public AdditiveExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(
                MultiplicativeExpression::new,
                p1->{
                    p.mandatory( AdditiveExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken( JavaTokenType.OPERATOR, "+").sendTo(this::setValue);
                    p.mandatory( MultiplicativeExpression::new ).sendTo(this::addChild);
                    return this;
                },
                p1->{
                    p.mandatory( AdditiveExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken( JavaTokenType.OPERATOR, "-").sendTo(this::setValue);
                    p.mandatory( MultiplicativeExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
