package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class PreincrementExpression extends Node {
    /*
        <preincrement expression> ::= '++' <unary expression>
     */
    public PreincrementExpression(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.OPERATOR, "+");
        mandatoryToken( reader, JavaTokenType.OPERATOR, "+");
        setValue("++");
    }
}
