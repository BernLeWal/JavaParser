package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class InclusiveOrExpression extends Node {
    /*
    <inclusive or expression> ::= <exclusive or expression>
                                | <inclusive or expression> '|' <exclusive or expression>
     */
    public InclusiveOrExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                ExclusiveOrExpression::new,
                r->{
                    p.mandatory(  InclusiveOrExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "|");
                    p.mandatory(  ExclusiveOrExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
