package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ShiftExpression extends Node {
    /*
        <shift expression> ::= <additive expression>
                            | <shift expression> '<<' <additive expression>
                            | <shift expression> '>>' <additive expression>
                            | <shift expression> '>>>' <additive expression>
     */
    public ShiftExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                AdditiveExpression::new,
                r->{
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.SHIFT, "<<").sendTo(this::setValue);
                    mandatory( r, AdditiveExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.SHIFT, ">>").sendTo(this::setValue);
                    mandatory( r, AdditiveExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.SHIFT, ">>>").sendTo(this::setValue);
                    mandatory( r, AdditiveExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
