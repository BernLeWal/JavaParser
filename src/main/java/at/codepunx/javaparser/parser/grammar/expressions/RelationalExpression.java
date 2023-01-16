package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class RelationalExpression extends Node {
    /*
    <relational expression> ::= <shift expression>
                                | <relational expression> '<' <shift expression>
                                | <relational expression> '>' <shift expression>
                                | <relational expression> '<=' <shift expression>
                                | <relational expression> '>=' <shift expression>
                                | <relational expression> instanceof <reference type>
     */
    public RelationalExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ShiftExpression::new,
                r->{
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.RELATION, "<").sendTo(this::setValue);
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.RELATION, ">").sendTo(this::setValue);
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.RELATION, "<=").sendTo(this::setValue);
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.RELATION, ">=").sendTo(this::setValue);
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.KEYWORD, "instanceof").sendTo(this::setValue);
                    mandatory( r, ShiftExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
