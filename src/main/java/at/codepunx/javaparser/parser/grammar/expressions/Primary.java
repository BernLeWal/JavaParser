package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class Primary extends Node {
    /*
    <primary> ::= <primary no new array> | <array creation expression>
     */
    public Primary(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                PrimaryNoNewArray::new,
                ArrayCreationExpression::new
        ).sendTo(this::addChild);
    }
}
