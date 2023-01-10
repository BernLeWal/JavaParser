package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;

public class VisibilityDeclaration extends Node {
    public enum Visibility {
        PUBLIC("public"),
        PROTECTED("protected"),
        PRIVATE("private"),
        DEFAULT("");

        final String value;

        Visibility(String value) {
            this.value = value;
        }
    }

    @Getter
    private final Visibility visibility;

    public VisibilityDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            if ( reader.tryReadToken( JavaTokenType.KEYWORD, Visibility.PUBLIC.value) )
                visibility = Visibility.PUBLIC;
            else if ( reader.tryReadToken( JavaTokenType.KEYWORD, Visibility.PROTECTED.value) )
                visibility = Visibility.PROTECTED;
            else if ( reader.tryReadToken( JavaTokenType.KEYWORD, Visibility.PRIVATE.value) )
                visibility = Visibility.PRIVATE;
            else
                visibility = Visibility.DEFAULT;
            if ( visibility!=Visibility.DEFAULT )
                reader.readToken( JavaTokenType.KEYWORD );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

}
