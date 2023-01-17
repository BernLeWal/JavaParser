package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PostfixExpression extends Node {
    /*
        <postfix expression> ::= <primary>
                              | <expression name>
                              | <postincrement expression>
                              | <postdecrement expression>
     */
    public PostfixExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                Primary::new,
                ExpressionName::new,
                PostincrementExpression::new,
                PostdecrementExpression::new
        ).sendTo(this::addChild);
    }
}
