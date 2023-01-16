package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class AndExpression extends Node {
    /*
    <and expression> ::= <equality expression>
                        | <and expression> '&' <equality expression>
     */
    public AndExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                EqualityExpression::new,
                r->{
                    mandatory( r, AndExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "&");
                    mandatory( r, EqualityExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
