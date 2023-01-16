package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class AdditiveExpression extends Node {
    /*
        <additive expression> ::= <multiplicative expression>
                                | <additive expression> '+' <multiplicative expression>
                                | <additive expression> '-' <multiplicative expression>
     */
    public AdditiveExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                MultiplicativeExpression::new,
                r->{
                    mandatory( r, AdditiveExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "+").sendTo(this::setValue);
                    mandatory( r, MultiplicativeExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, AdditiveExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.OPERATOR, "-").sendTo(this::setValue);
                    mandatory( r, MultiplicativeExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
