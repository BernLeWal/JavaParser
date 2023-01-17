package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class AndExpression extends Node {
    /*
    <and expression> ::= <equality expression>
                        | <and expression> '&' <equality expression>
     */
    public AndExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                EqualityExpression::new,
                r->{
                    p.mandatory( AndExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "&");
                    p.mandatory( EqualityExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
