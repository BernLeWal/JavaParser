package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PostincrementExpression extends Node {
    /*
        <postincrement expression> ::= <postfix expression> '++'
     */
    public PostincrementExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatory( PostfixExpression::new ).sendTo(this::addChild);
        p.mandatoryToken( JavaTokenType.OPERATOR, "+");
        p.mandatoryToken( JavaTokenType.OPERATOR, "+");
        setValue("++");
    }
}
