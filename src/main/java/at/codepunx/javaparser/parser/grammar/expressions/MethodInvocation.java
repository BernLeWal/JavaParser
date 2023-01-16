package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ExpressionName;
import at.codepunx.javaparser.parser.grammar.types.Identifier;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class MethodInvocation extends Node {
    /*
        <method invocation> ::= <method name> ( <argument list>? )
                            | <primary> . <identifier> ( <argument list>? )
                            | super . <identifier> ( <argument list>? )
        <method name> ::= <identifier> | <ambiguous name>. <identifier>
        <argument list> ::= <expression> | <argument list> , <expression>
    */
    public MethodInvocation(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                r-> {
                    mandatory(r, ExpressionName::new)
                            .sendTo(n -> {
                                this.addChild(n);
                                this.setValue(n.getValue());
                            });
                    return null;
                },
//                r-> {
//                    mandatory(r, Primary::new).sendTo(this::addChild);
//                    mandatoryToken(r, JavaTokenType.DOT);
//                    mandatoryToken( reader, JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
//                    return null;
//                },
                r-> {
                    mandatoryToken(r, JavaTokenType.KEYWORD, "super");
                    mandatoryToken(r, JavaTokenType.DOT);
                    mandatoryToken( reader, JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
                    addChild( new Identifier("super") );
                    return null;
                }
        );

        mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_OPEN );
        var firstArgument = optional( reader, Expression::new );
        if ( !firstArgument.isEmpty() ) {
            firstArgument.sendTo(this::addChild);
            while ( !optionalToken( reader, JavaTokenType.COMMA ).isEmpty() ) {
                mandatory( reader, Expression::new ).sendTo(this::addChild);
            }
        }
        mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_CLOSE );
    }
}
