package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class PostincrementExpression extends Node {
    /*
        <postincrement expression> ::= <postfix expression> '++'
     */
    public PostincrementExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatory( reader, PostfixExpression::new ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.OPERATOR, "+");
        mandatoryToken( reader, JavaTokenType.OPERATOR, "+");
        setValue("++");
    }
}
