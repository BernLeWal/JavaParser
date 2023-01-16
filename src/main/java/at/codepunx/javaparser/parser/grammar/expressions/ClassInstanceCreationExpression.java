package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.PrimitiveType;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class ClassInstanceCreationExpression extends Node {
    /*
        <class instance creation expression> ::= new <class type> ( <argument list>? )
        <argument list> ::= <expression> | <argument list> , <expression>
    */
    public ClassInstanceCreationExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.KEYWORD, "new");
        mandatory( reader, ReferenceType::new ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_OPEN );
        var firstArgument = optional( reader, Expression::new);
        if ( !firstArgument.isEmpty() ) {
            firstArgument.sendTo(this::addChild);
            while ( !optionalToken( reader, JavaTokenType.COMMA ).isEmpty() ) {
                mandatory( reader, Expression::new ).sendTo(this::addChild);
            }
        }
        mandatoryToken( reader, JavaTokenType.ROUND_BRACKET_CLOSE );
    }
}
