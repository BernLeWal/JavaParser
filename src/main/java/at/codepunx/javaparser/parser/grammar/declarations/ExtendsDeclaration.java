package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.mandatoryToken;

public class ExtendsDeclaration extends Node {
    public ExtendsDeclaration(TokenReader<JavaTokenType> reader ) throws ParseException {
        mandatoryToken( reader, JavaTokenType.KEYWORD, "extends");
        setValue(new ReferenceType(reader).getValue());
    }
}
