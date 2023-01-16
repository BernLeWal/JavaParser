package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ExclusiveOrExpression extends Node {
    /*
    <exclusive or expression> ::= <and expression>
                                | <exclusive or expression> '^' <and expression>
     */
    public ExclusiveOrExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                AndExpression::new,
                r->{
                    mandatory( r, ExclusiveOrExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "^");
                    mandatory( r, AndExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
