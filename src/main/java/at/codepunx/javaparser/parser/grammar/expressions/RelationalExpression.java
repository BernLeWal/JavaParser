package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class RelationalExpression extends Node {
    /*
    <relational expression> ::= <shift expression>
                                | <relational expression> '<' <shift expression>
                                | <relational expression> '>' <shift expression>
                                | <relational expression> '<=' <shift expression>
                                | <relational expression> '>=' <shift expression>
                                | <relational expression> instanceof <reference type>
     */
    public RelationalExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                ShiftExpression::new,
                r->{
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.RELATION, "<").sendTo(this::setValue);
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.RELATION, ">").sendTo(this::setValue);
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.RELATION, "<=").sendTo(this::setValue);
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.RELATION, ">=").sendTo(this::setValue);
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.KEYWORD, "instanceof").sendTo(this::setValue);
                    p.mandatory(  ShiftExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
