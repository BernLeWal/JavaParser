package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.parser.grammar.types.Identifier;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class MethodInvocation extends Node {
    /*
        <method invocation> ::= <method name> ( <argument list>? )
                            | <primary> . <identifier> ( <argument list>? )
                            | super . <identifier> ( <argument list>? )
        <method name> ::= <identifier> | <ambiguous name>. <identifier>
        <argument list> ::= <expression> | <argument list> , <expression>
    */
    public MethodInvocation(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(  
                r-> {
                    p.mandatory( ExpressionName::new)
                            .sendTo(n -> {
                                this.addChild(n);
                                this.setValue(n.getValue());
                            });
                    return this;
                },
//                r-> {
//                    p.mandatory( Primary::new).sendTo(this::addChild);
//                    p.mandatoryToken( JavaTokenType.DOT);
//                    p.mandatoryToken( JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
//                    return this;
//                },
                r-> {
                    p.mandatoryToken( JavaTokenType.KEYWORD, "super");
                    p.mandatoryToken( JavaTokenType.DOT);
                    p.mandatoryToken( JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
                    addChild( new Identifier( "super") );
                    return this;
                }
        );

        p.mandatoryToken( JavaTokenType.ROUND_BRACKET_OPEN );
        var firstArgument = p.optional( Expression::new );
        if ( !firstArgument.isEmpty() ) {
            firstArgument.sendTo(this::addChild);
            while ( !p.optionalToken( JavaTokenType.COMMA ).isEmpty() ) {
                p.mandatory( Expression::new ).sendTo(this::addChild);
            }
        }
        p.mandatoryToken( JavaTokenType.ROUND_BRACKET_CLOSE );
    }
}
