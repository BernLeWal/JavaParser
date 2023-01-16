package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class AssignmentOperator extends Node {
    /*
        <assignment operator> ::= = | *= | /= | %= | += | -= | <<= | >>= | >>>= | &= | ^= | |=
     */
    public AssignmentOperator(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.ASSIGNMENT ).sendTo(this::setValue);
    }
}
