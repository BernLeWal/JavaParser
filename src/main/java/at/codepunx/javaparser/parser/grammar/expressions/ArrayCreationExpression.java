package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.PrimitiveType;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ArrayCreationExpression extends Node {
    /*
        <array creation expression> ::= new <primitive type> <dim exprs> <dims>?
                                      | new <class or interface type> <dim exprs> <dims>?
        <dim exprs> ::= <dim expr> | <dim exprs> <dim expr>
        <dim expr> ::= [ <expression> ]
        <dims> ::= [ ] | <dims> [ ]
     */
    public ArrayCreationExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.KEYWORD, "new");
        p.mandatoryOneOf(  
                PrimitiveType::new,
                ReferenceType::new
        ).sendTo(n->setValue(n.getValue()));
        p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_OPEN);
        p.mandatory( Expression::new ).sendTo( this::addChild );
        p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_CLOSE);
        p.multiple( r->{
                p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_OPEN);
                p.mandatory( Expression::new ).sendTo( this::addChild );
                p.mandatoryToken( JavaTokenType.SQUARE_BRACKET_CLOSE);
        });
    }
}
