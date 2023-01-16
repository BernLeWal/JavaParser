package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;


public class PrimitiveType extends Node {
    public PrimitiveType(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken(reader, JavaTokenType.PRIMITIVE ).sendTo(this::setValue);
    }
}
