package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ExclusiveOrExpression extends Node {
    /*
    <exclusive or expression> ::= <and expression>
                                | <exclusive or expression> '^' <and expression>
     */
    public ExclusiveOrExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                AndExpression::new,
                r->{
                    p.mandatory(  ExclusiveOrExpression::new ).sendTo(this::addChild);
                    p.mandatoryToken(  JavaTokenType.OPERATOR, "^");
                    p.mandatory(  AndExpression::new ).sendTo(this::addChild);
                    return this;
                }
        ).sendTo(this::addChild);
    }
}
