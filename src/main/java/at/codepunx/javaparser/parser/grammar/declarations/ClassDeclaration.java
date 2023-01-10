package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;
import lombok.Setter;

public class ClassDeclaration extends Node {
    /*
    <class declaration> ::= <class modifiers>? class <identifier> <super>? <interfaces>? <class body>
    <class modifiers> ::= <class modifier> | <class modifiers> <class modifier>
    <class modifier> ::= public | abstract | final
    <super> ::= extends <class type>
    <interfaces> ::= implements <interface type list>
    <interface type list> ::= <interface type> | <interface type list> , <interface type>
     */
    public ClassDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            visibility = new VisibilityDeclaration( reader ).getVisibility();

            reader.readToken( JavaTokenType.KEYWORD, "class");
            setValue( reader.readToken( JavaTokenType.IDENTIFIER ).getValue() );

        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    @Getter
    @Setter
    private VisibilityDeclaration.Visibility visibility;

    public String getName() { return getValue(); }
}
