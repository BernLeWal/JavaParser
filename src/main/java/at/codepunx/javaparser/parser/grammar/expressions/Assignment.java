package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class Assignment extends Node {
    /*
        <assignment> ::= <left hand side> <assignment operator> <assignment expression>
TODO    <left hand side> ::= <expression name> | <field access> | <array access>
     */
    public Assignment(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.IDENTIFIER).sendTo(this::setValue);
        p.mandatory( AssignmentOperator::new).sendTo(n->setAttribute(JavaTokenType.ASSIGNMENT.name(), n));
        p.mandatory( AssignmentExpression::new).sendTo(this::addChild);
    }
}
