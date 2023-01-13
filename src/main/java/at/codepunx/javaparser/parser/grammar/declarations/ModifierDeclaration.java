package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.KeywordTypeInterface;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.ToString;

@ToString
public class ModifierDeclaration extends Node {

    public ModifierDeclaration(KeywordTypeInterface keyword, TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            reader.readToken(JavaTokenType.KEYWORD, keyword.value());
            setValue( keyword.value() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public ModifierDeclaration(KeywordTypeInterface keyword) {
        setValue(keyword.value());
    }

}
