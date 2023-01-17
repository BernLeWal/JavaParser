package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ArrayAccess extends Node {
    /*
        <array access> ::= <expression name> '[' <expression> ']'
                        | <primary no new array> '[' <expression> ']'
     */
    public ArrayAccess(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        System.out.println(toString());
        p.mandatoryOneOf(  
                ExpressionName::new,
                PrimaryNoNewArray::new
        ).sendTo(this::addChild);
        p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_OPEN );
        p.mandatory( Expression::new ).sendTo(this::addChild);
        p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_CLOSE );
    }
}
