package at.codepunx.javaparser.tokenizer;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class TokenReader<T extends TokenTypeInterface> implements Cloneable {

    private final List<Token<T>> tokens;
    private final ListIterator<Token<T>> it;

    @Getter
    @Setter
    private boolean skipWhitespaceTokens = true;

    @Getter
    private List<T> whitespaceTokenTypes;


    public TokenReader(List<Token<T>> tokens) {
        this.tokens = tokens;
        this.it = tokens.listIterator();
    }

    public TokenReader(TokenReader<T> that) {
        this.tokens = that.tokens;
        this.it = tokens.listIterator(that.it.nextIndex());
        this.skipWhitespaceTokens = that.skipWhitespaceTokens;
        this.whitespaceTokenTypes = that.whitespaceTokenTypes;
    }

    @Override
    public Object clone() {
        return new TokenReader<>(this);
    }

    public void revertTo(TokenReader<T> that ) {       // TODO
        int index = that.it.hasNext() ? (that.it.nextIndex()-1) : ( that.it.hasPrevious() ? (that.it.previousIndex()+1) : 0);
        while( this.it.previousIndex() > index ) {
            this.it.previous();
        }
    }


    public boolean hasNext() {
        return it.hasNext();
    }

    public void setWhitespaceTokenTypes(T[] tokenTypes) {
        whitespaceTokenTypes = Arrays.stream(tokenTypes).toList();
    }

    public Token<T> readToken(T tokenType ) throws TokenReaderException {
        if (!it.hasNext() )
            throw new TokenReaderException("End of token-stream!");
        Token<T> token = next();
        if ( token.getType() != tokenType ) {
            previous();
            throw new TokenReaderException( String.format("Invalid token type '%s', expected was '%s'!", token.getType(), tokenType) );
        }
        System.out.println( String.format("read Token %s(%s)", token.getType().name(), token.getValue() ) );
        return token;
    }

    public Token<T> readToken(T tokenType, String value ) throws TokenReaderException {
        if (!it.hasNext() )
            throw new TokenReaderException("End of token-stream!");
        Token<T> token = next();
        if ( token.getType() != tokenType || !token.getValue().equals(value) ) {
            previous();
            throw new TokenReaderException( String.format("Invalid token type '%s'/'%s', expected was '%s'/'%s'!"
                    , token.getType(), token.getValue(), tokenType, value) );
        }
        System.out.println( String.format("read Token %s(%s)", token.getType().name(), token.getValue() ) );
        return token;
    }

    public boolean tryReadToken(T tokenType) {
        if (!it.hasNext() )
            return false;
        Token<T> token = next();
        previous();
        return ( token.getType() == tokenType );
    }

    public boolean tryReadToken(T tokenType, String value) {
        if (!it.hasNext() )
            return false;
        Token<T> token = next();
        previous();
        return (token.getType()==tokenType) && (token.getValue().equals(value));
    }


    private Token<T> next() {
        Token<T> token = it.next();
        if ( skipWhitespaceTokens && whitespaceTokenTypes !=null && !whitespaceTokenTypes.isEmpty()) {
            while( whitespaceTokenTypes.contains(token.getType()) && it.hasNext() ) {
                token = it.next();
            }
        }
        return token;
    }

    private Token<T> previous() {
        Token<T> token = it.previous();
        if ( skipWhitespaceTokens && whitespaceTokenTypes !=null && !whitespaceTokenTypes.isEmpty()) {
            while( whitespaceTokenTypes.contains(token.getType()) && it.hasPrevious() ) {
                token = it.previous();
            }
        }
        return token;
    }

}
