package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class UnaryExpression extends Node {
    /*
        <unary expression> ::= <preincrement expression>
                            | <predecrement expression>
                            | '+' <unary expression>
                            | '-' <unary expression>
                            | <unary expression not plus minus>
     */
    public UnaryExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                PreincrementExpression::new,
                PredecrementExpression::new,
                r->{
                    mandatoryToken( r, JavaTokenType.OPERATOR, "+").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatoryToken( r, JavaTokenType.OPERATOR, "-").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                UnaryExpressionNotPlusMinus::new
        ).sendTo(this::addChild);
    }
}
