package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ConditionalOrExpression extends Node {
    /*
    <conditional or expression> ::= <conditional and expression> | <conditional or expression> '||' <conditional and expression>
     */
    public ConditionalOrExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                ConditionalAndExpression::new,
                r->{
                    p.mandatory(  ConditionalOrExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken( JavaTokenType.OPERATOR, "|");
                    p.mandatoryToken( JavaTokenType.OPERATOR, "|");
                    p.mandatory( ConditionalAndExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
