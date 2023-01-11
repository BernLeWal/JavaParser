package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.KeywordTypeInterface;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;

import java.util.EnumSet;

public class ModifierDeclaration<T extends Enum<T> > extends Node {
    @Getter
    private T keyword = null;

    public ModifierDeclaration(Class<T> enumClass, TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            boolean found = false;
            for ( var e : EnumSet.allOf(enumClass) ) {
                if ( reader.tryReadToken( JavaTokenType.KEYWORD, ((KeywordTypeInterface)e).value()) ) {
                    keyword = e;
                    found = true;
                    break;
                }
            }

            if ( found )
                reader.readToken( JavaTokenType.KEYWORD );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
