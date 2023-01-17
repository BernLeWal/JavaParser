package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class Primary extends Node {
    /*
    <primary> ::= <primary no new array> | <array creation expression>
     */
    public Primary(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                PrimaryNoNewArray::new,
                ArrayCreationExpression::new
        ).sendTo(this::addChild);
    }
}
