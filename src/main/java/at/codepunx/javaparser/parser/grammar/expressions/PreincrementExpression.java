package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PreincrementExpression extends Node {
    /*
        <preincrement expression> ::= '++' <unary expression>
     */
    public PreincrementExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.OPERATOR, "+");
        p.mandatoryToken( JavaTokenType.OPERATOR, "+");
        p.mandatory( UnaryExpression::new ).sendTo(this::addChild);
        setValue("++");
    }
}
