package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ConditionalAndExpression extends Node {
    /*
    <conditional and expression> ::= <inclusive or expression>
                                    | <conditional and expression> '&&' <inclusive or expression>
     */
    public ConditionalAndExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                InclusiveOrExpression::new,
                r->{
                    p.mandatory(  ConditionalAndExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken( JavaTokenType.OPERATOR, "&");
                    p.mandatoryToken( JavaTokenType.OPERATOR, "&");
                    p.mandatory( InclusiveOrExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
