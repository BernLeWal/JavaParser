package at.codepunx.javaparser.parser;

import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.NodeProvider;
import at.codepunx.javaparser.parser.grammar.ValueProvider;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.function.Consumer;
import java.util.function.Function;

public class Parser {
    public static NodeProvider mandatory(TokenReader<JavaTokenType> reader, Function<TokenReader<JavaTokenType>, Node> func) throws ParseException {
        return new NodeProvider( func.apply(reader) );
    }

    public static NodeProvider optional(TokenReader<JavaTokenType> reader, Function<TokenReader<JavaTokenType>, Node> func) {
        TokenReader<JavaTokenType> backupReader = (TokenReader<JavaTokenType>)reader.clone();
        try {
            return new NodeProvider( func.apply(reader) );
        }
        catch ( ParseException e ) {
            reader.revertTo(backupReader);
            return new NodeProvider();
        }
    }


    public static ValueProvider mandatoryToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType) throws ParseException {
        try {
            return new ValueProvider( reader.readToken(tokenType).getValue() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public static void mandatoryToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType, String value) throws ParseException {
        try {
            reader.readToken(tokenType, value);
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public static ValueProvider optionalToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType) {
        if ( reader.tryReadToken(tokenType) ) {
            try {
                return new ValueProvider(reader.readToken(tokenType).getValue());
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return new ValueProvider();
    }

    public static boolean optionalToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType, String value) {
        if ( reader.tryReadToken(tokenType, value) ) {
            try {
                reader.readToken(tokenType, value);
                return true;
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return false;
    }


    // TODO calculate created nodes count internally, take away dependency to parent.getCount()
    public static int multiple(Node parent, TokenReader<JavaTokenType> reader, Consumer<TokenReader<JavaTokenType>> func) throws ParseException {
        int startChildCount = parent.getCount();
        int childCount;
        do {
            childCount = parent.getCount();
            TokenReader<JavaTokenType> backupReader = (TokenReader<JavaTokenType>)reader.clone();
            try {
                func.accept(reader);
            }
            catch ( ParseException e ) {
                reader.revertTo(backupReader);
                break;
            }
        } while ( childCount < parent.getCount() );
        return parent.getCount() - startChildCount;
    }
}
