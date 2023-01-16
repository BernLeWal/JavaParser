package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class InclusiveOrExpression extends Node {
    /*
    <inclusive or expression> ::= <exclusive or expression>
                                | <inclusive or expression> '|' <exclusive or expression>
     */
    public InclusiveOrExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ExclusiveOrExpression::new,
                r->{
                    mandatory( r, InclusiveOrExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "|");
                    mandatory( r, ExclusiveOrExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
