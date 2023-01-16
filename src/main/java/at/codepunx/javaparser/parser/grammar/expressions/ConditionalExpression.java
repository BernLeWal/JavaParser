package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.NodeProvider;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ConditionalExpression extends Node {
    /*
        <conditional expression> ::= <conditional or expression>
                                | <conditional or expression> '?' <expression> ':' <conditional expression>
        <expression> ::= <assignment expression>
     */
    public ConditionalExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ConditionalOrExpression::new,
                r->{
                    mandatory(r, ConditionalOrExpression::new).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "?");
                    mandatory(r, Expression::new).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, ":");
                    mandatory(r, ConditionalExpression::new).sendTo(this::addChild);
                    return null;
                }
                ).sendTo(this::addChild);
    }
}
