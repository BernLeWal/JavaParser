package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class ImplementsDeclaration extends Node {
    public ImplementsDeclaration(boolean readFirst, TokenReader<JavaTokenType> reader ) throws ParseException {
        try {
            if ( readFirst )
                reader.readToken( JavaTokenType.KEYWORD, "implements");
            else // read next
                reader.readToken( JavaTokenType.COMMA);

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

    public ImplementsDeclaration(TokenReader<JavaTokenType> reader ) throws ParseException {
        this(true, reader);
    }


    public String getInterfaceName() { return getValue(); }
}
