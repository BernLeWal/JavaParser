package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class Identifier extends Node {
    public Identifier(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.IDENTIFIER ).sendTo(this::setValue);
    }

    public Identifier(String identifier) {
        setValue(identifier);
    }
}
