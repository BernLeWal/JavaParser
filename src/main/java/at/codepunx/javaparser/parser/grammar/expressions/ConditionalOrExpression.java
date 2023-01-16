package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ConditionalOrExpression extends Node {
    /*
    <conditional or expression> ::= <conditional and expression> | <conditional or expression> '||' <conditional and expression>
     */
    public ConditionalOrExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ConditionalAndExpression::new,
                r->{
                    mandatory( r, ConditionalOrExpression::new ).sendTo(this::addChild);
                    mandatoryToken( reader, JavaTokenType.OPERATOR, "|");
                    mandatoryToken( reader, JavaTokenType.OPERATOR, "|");
                    mandatory( reader, ConditionalAndExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
