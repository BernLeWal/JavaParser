package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.mandatoryToken;

public class ImportDeclaration extends Node {
    /*
    <import declaration> ::= <single type import declaration> | <type import on demand declaration>
    <single type import declaration> ::= import <type name> ;
    <type import on demand declaration> ::= import <package name> . * ;
     */
    public ImportDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryToken( reader, JavaTokenType.KEYWORD, "import");
        setValue(new ReferenceType(reader).getValue());
        mandatoryToken( reader, JavaTokenType.SEMIKOLON);
    }
}
