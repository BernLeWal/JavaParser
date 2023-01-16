package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class AssignmentExpression extends Node {
    /*
        <assignment expression> ::= <conditional expression>
                                | <assignment>
     */
    public AssignmentExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                ConditionalExpression::new,
                Assignment::new
        ).sendTo(this::addChild);
        mandatoryToken( reader, JavaTokenType.SEMIKOLON);

//        while ( !reader.tryReadToken(JavaTokenType.SEMIKOLON) ) {
//            System.out.println("Expression: skipped token " + reader.next().getValue());
//        }
    }
}
