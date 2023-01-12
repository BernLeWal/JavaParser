package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.mandatoryToken;

public class ImplementsDeclaration extends Node {
    public ImplementsDeclaration(boolean readFirst, TokenReader<JavaTokenType> reader ) throws ParseException {
        if ( readFirst )
            mandatoryToken(reader, JavaTokenType.KEYWORD, "implements");
        else // read next
            mandatoryToken(reader, JavaTokenType.COMMA);
        setValue(new ReferenceType(reader).getValue());
    }

    public ImplementsDeclaration(TokenReader<JavaTokenType> reader ) throws ParseException {
        this(true, reader);
    }
}
