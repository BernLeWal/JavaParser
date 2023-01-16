package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class MultiplicativeExpression extends Node {
    /*
        <multiplicative expression> ::= <unary expression>
                                      | <multiplicative expression> '*' <unary expression>
                                      | <multiplicative expression> '/' <unary expression>
                                      | <multiplicative expression> '%' <unary expression>
     */
    public MultiplicativeExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                UnaryExpression::new,
                r->{
                    mandatory( r, MultiplicativeExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "*").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, MultiplicativeExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "/").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, MultiplicativeExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "%").sendTo(this::setValue);
                    mandatory( r, UnaryExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
