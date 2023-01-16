package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.PrimitiveType;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class CastExpression extends Node {
    /*
        <cast expression> ::= '(' <primitive type> ')' <unary expression>
                            | '(' <reference type> ')' <unary expression not plus minus>
     */
    public CastExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                r->{
                    mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_OPEN);
                    mandatory( reader, PrimitiveType::new ).sendTo(n->setValue(n.getValue()));
                    mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_CLOSE);
                    mandatory( reader, UnaryExpression::new).sendTo(this::addChild);
                    return null;
                },
                r->{
                    mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_OPEN);
                    mandatory( reader, ReferenceType::new ).sendTo(n->setValue(n.getValue()));
                    mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_CLOSE);
                    mandatory( reader, UnaryExpressionNotPlusMinus::new).sendTo(this::addChild);
                    return null;
                }
        );
    }
}
