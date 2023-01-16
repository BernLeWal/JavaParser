package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.mandatory;
import static at.codepunx.javaparser.parser.Parser.mandatoryToken;

public class PostdecrementExpression extends Node {
    /*
        <postdecrement expression> ::= <postfix expression> '--'
     */
    public PostdecrementExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatory( reader, PostfixExpression::new ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.OPERATOR, "-");
        mandatoryToken( reader, JavaTokenType.OPERATOR, "-");
        setValue("--");
    }
}
