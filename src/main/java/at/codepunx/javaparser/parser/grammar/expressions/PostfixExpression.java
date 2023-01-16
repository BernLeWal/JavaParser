package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class PostfixExpression extends Node {
    /*
        <postfix expression> ::= <primary>
                              | <expression name>
                              | <postincrement expression>
                              | <postdecrement expression>
     */
    public PostfixExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                Primary::new,
                ExpressionName::new
//                PostincrementExpression::new,
//                PostdecrementExpression::new
        ).sendTo(this::addChild);
    }
}
