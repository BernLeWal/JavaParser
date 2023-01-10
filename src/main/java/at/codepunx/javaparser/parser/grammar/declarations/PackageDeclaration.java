package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PackageDeclaration extends Node {
    /*
    <package declaration> ::= package <package name> ;
    <package name> ::= <identifier> | <package name> . <identifier>
     */
    public PackageDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            reader.readToken( JavaTokenType.KEYWORD, "package");

            StringBuilder packageName = new StringBuilder();
            packageName.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                packageName.append(reader.readToken(JavaTokenType.DOT).getValue());
                packageName.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( packageName.toString() );

            reader.readToken(JavaTokenType.SEMIKOLON);
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public String getPackageName() {
        return getValue();
    }
}
