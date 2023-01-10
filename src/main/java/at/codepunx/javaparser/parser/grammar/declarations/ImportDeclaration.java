package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ImportDeclaration extends Node {
    /*
    <import declaration> ::= <single type import declaration> | <type import on demand declaration>
    <single type import declaration> ::= import <type name> ;
    <type import on demand declaration> ::= import <package name> . * ;
     */
    public ImportDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            reader.readToken( JavaTokenType.KEYWORD, "import");

            StringBuilder fqcn = new StringBuilder();
            fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
                fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );

            reader.readToken(JavaTokenType.SEMIKOLON);
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public String getFullQualifiedClassName() {
        return getValue();
    }
}