package at.codepunx.javaparser.tokenizer.impl;

import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.Tokenizer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaTokenizer extends Tokenizer<JavaTokenType> {

    public JavaTokenizer() {
        super(JavaTokenType.values());
    }

    @Override
    protected Token<JavaTokenType> addToken(JavaTokenType possibleTokenType, String currentText) {
        if ( possibleTokenType.equals(JavaTokenType.WHITESPACE) && !currentText.contains("\n"))
            return null; // skip extra tokens for the single space char here

        var token = super.addToken(possibleTokenType, currentText);

        System.out.println( token );
        return token;
    }
}
