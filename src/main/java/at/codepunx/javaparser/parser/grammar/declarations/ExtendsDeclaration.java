package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ExtendsDeclaration extends Node {
    public ExtendsDeclaration(TokenReader<JavaTokenType> reader ) throws ParseException {
        try {
            reader.readToken( JavaTokenType.KEYWORD, "extends");

            StringBuilder fqcn = new StringBuilder();
            fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
                fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public String getBaseClassName() { return getValue(); }
}
