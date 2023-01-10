package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ImportNode extends Node {
    public ImportNode(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            reader.readToken( JavaTokenType.KEYWORD, "import");

            StringBuilder fqcn = new StringBuilder();
            fqcn.append(reader.readToken(JavaTokenType.NAME).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
                fqcn.append(reader.readToken(JavaTokenType.NAME).getValue());
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
