package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class PackageNode extends Node {
    public PackageNode(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            reader.readToken( JavaTokenType.KEYWORD, "package");

            StringBuilder packageName = new StringBuilder();
            packageName.append(reader.readToken(JavaTokenType.NAME).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                packageName.append(reader.readToken(JavaTokenType.DOT).getValue());
                packageName.append(reader.readToken(JavaTokenType.NAME).getValue());
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
