package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class AssignmentOperator extends Node {
    /*
        <assignment operator> ::= = | *= | /= | %= | += | -= | <<= | >>= | >>>= | &= | ^= | |=
     */
    public AssignmentOperator(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.ASSIGNMENT ).sendTo(this::setValue);
    }
}
