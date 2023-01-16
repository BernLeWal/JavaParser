package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ArrayAccess extends Node {
    /*
        <array access> ::= <expression name> '[' <expression> ']'
                        | <primary no new array> '[' <expression> ']'
     */
    public ArrayAccess(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ExpressionName::new
//                PrimaryNoNewArray::new
        ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_OPEN );
        mandatory( reader, Expression::new ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_CLOSE );
    }
}
