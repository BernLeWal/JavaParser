package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ShiftExpression extends Node {
    /*
        <shift expression> ::= <additive expression>
                            | <shift expression> '<<' <additive expression>
                            | <shift expression> '>>' <additive expression>
                            | <shift expression> '>>>' <additive expression>
     */
    public ShiftExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                AdditiveExpression::new,
                r->{
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.SHIFT, "<<").sendTo(this::setValue);
                    p.mandatory(  AdditiveExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.SHIFT, ">>").sendTo(this::setValue);
                    p.mandatory(  AdditiveExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.SHIFT, ">>>").sendTo(this::setValue);
                    p.mandatory(  AdditiveExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
