package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PackageDeclaration extends Node {
    /*
    <package declaration> ::= package <package name> ;
    <package name> ::= <identifier> | <package name> . <identifier>
     */
    public PackageDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.KEYWORD, "package");
        setValue(new ReferenceType(reader).getValue());
        mandatoryToken( reader, JavaTokenType.SEMIKOLON );
    }
}
