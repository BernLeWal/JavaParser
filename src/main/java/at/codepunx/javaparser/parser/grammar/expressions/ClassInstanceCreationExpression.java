package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ClassInstanceCreationExpression extends Node {
    /*
        <class instance creation expression> ::= new <class type> ( <argument list>? )
        <argument list> ::= <expression> | <argument list> , <expression>
    */
    public ClassInstanceCreationExpression(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.KEYWORD, "new");
        p.mandatory( ReferenceType::new ).sendTo(n->setValue(n.getValue()));
        p.mandatoryToken( JavaTokenType.ROUND_BRACKET_OPEN );
        var firstArgument = p.optional( Expression::new);
        if ( !firstArgument.isEmpty() ) {
            firstArgument.sendTo(this::addChild);
            while ( !p.optionalToken( JavaTokenType.COMMA ).isEmpty() ) {
                p.mandatory( Expression::new ).sendTo(this::addChild);
            }
        }
        p.mandatoryToken( JavaTokenType.ROUND_BRACKET_CLOSE );
    }
}
