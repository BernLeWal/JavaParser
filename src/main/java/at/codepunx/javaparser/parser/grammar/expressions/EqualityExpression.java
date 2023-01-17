package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class EqualityExpression extends Node {
    /*
    <equality expression> ::= <relational expression>
                           | <equality expression> '==' <relational expression>
                           | <equality expression> '!=' <relational expression>
     */
    public EqualityExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                RelationalExpression::new,
                r->{
                    p.mandatory(  EqualityExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.ASSIGNMENT, "==").sendTo(this::setValue);
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    return this;
                },
                r->{
                    p.mandatory(  EqualityExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.ASSIGNMENT, "!=").sendTo(this::setValue);
                    p.mandatory(  RelationalExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
