package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class EqualityExpression extends Node {
    /*
    <equality expression> ::= <relational expression>
                           | <equality expression> '==' <relational expression>
                           | <equality expression> '!=' <relational expression>
     */
    public EqualityExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                RelationalExpression::new,
                r->{
                    mandatory( r, EqualityExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.ASSIGNMENT, "==").sendTo(this::setValue);
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatory( r, EqualityExpression::new ).sendTo(this::addChild);
                    mandatoryToken( r, JavaTokenType.ASSIGNMENT, "!=").sendTo(this::setValue);
                    mandatory( r, RelationalExpression::new ).sendTo(this::addChild);
                    return null;
                }
        ).sendTo(this::addChild);
    }
}
