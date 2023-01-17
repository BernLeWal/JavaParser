package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class PrimitiveType extends Node {
    public PrimitiveType(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken(JavaTokenType.PRIMITIVE ).sendTo(this::setValue);
    }
}
