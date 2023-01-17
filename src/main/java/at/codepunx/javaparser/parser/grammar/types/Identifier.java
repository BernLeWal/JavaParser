package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class Identifier extends Node {
    public Identifier(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
    }

    public Identifier(String identifier) {
        super( null );
        setValue(identifier);
    }
}
