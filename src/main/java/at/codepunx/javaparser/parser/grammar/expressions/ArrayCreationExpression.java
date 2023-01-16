package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.PrimitiveType;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ArrayCreationExpression extends Node {
    /*
        <array creation expression> ::= new <primitive type> <dim exprs> <dims>?
                                      | new <class or interface type> <dim exprs> <dims>?
        <dim exprs> ::= <dim expr> | <dim exprs> <dim expr>
        <dim expr> ::= [ <expression> ]
        <dims> ::= [ ] | <dims> [ ]
     */
    public ArrayCreationExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.KEYWORD, "new");
        mandatoryOneOf( reader,
                PrimitiveType::new,
                ReferenceType::new
        ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_OPEN);
        mandatory( reader, Expression::new ).sendTo( this::addChild );
        mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_CLOSE);
        multiple( this, reader, r->{
                mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_OPEN);
                mandatory( reader, Expression::new ).sendTo( this::addChild );
                mandatoryToken( reader, JavaTokenType.SQUARE_BRACKET_CLOSE);
        });
    }
}
