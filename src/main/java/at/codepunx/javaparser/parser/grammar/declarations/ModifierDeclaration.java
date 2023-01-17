package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.KeywordTypeInterface;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.ToString;

@ToString
public class ModifierDeclaration extends Node {

    public ModifierDeclaration(Parser<JavaTokenType> p, KeywordTypeInterface keyword) throws ParseException {
        super( p );
        p.mandatoryToken(JavaTokenType.KEYWORD, keyword.value());
        setValue( keyword.value() );
    }

//    public ModifierDeclaration(KeywordTypeInterface keyword) {
//        setValue(keyword.value());
//    }

}
