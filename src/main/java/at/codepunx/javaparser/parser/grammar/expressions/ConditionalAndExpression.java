package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ConditionalAndExpression extends Node {
    /*
    <conditional and expression> ::= <inclusive or expression>
                                    | <conditional and expression> '&&' <inclusive or expression>
     */
    public ConditionalAndExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                InclusiveOrExpression::new,
                r->{
                    mandatory( r, ConditionalAndExpression::new ).sendTo(this::addChild);
                    mandatoryToken( reader, JavaTokenType.OPERATOR, "&");
                    mandatoryToken( reader, JavaTokenType.OPERATOR, "&");
                    mandatory( reader, InclusiveOrExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
