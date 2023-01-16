package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;


public class Assignment extends Node {
    /*
        <assignment> ::= <left hand side> <assignment operator> <assignment expression>
TODO    <left hand side> ::= <expression name> | <field access> | <array access>
     */
    public Assignment(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken(reader, JavaTokenType.IDENTIFIER).sendTo(this::setValue);
        mandatory(reader, AssignmentOperator::new).sendTo(n->setAttribute(JavaTokenType.ASSIGNMENT.name(), n));
        mandatory(reader, AssignmentExpression::new).sendTo(this::addChild);
    }
}
