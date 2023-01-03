package at.codepunx.javaparser.tokenizer;

import lombok.Getter;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer<T extends TokenTypeInterface> {
    @Getter
    protected final T[] allTokenTypes;
    @Getter
    protected ArrayList<Token<T>> tokens = new ArrayList<>();


    private BufferedReader reader;

    public Tokenizer(T[] allTokenTypes) {
        this.allTokenTypes = allTokenTypes;
    }

    public ArrayList<Token<T>> tokenize(InputStream input) throws TokenizerException {
        doTokenize(input);
        return tokens;
    }

    public ArrayList<Token<T>> tokenize(URI uri) throws TokenizerException {
        try {
            return tokenize(uri.toURL().openStream());
        } catch (IOException e) {
            throw new TokenizerException(e.getMessage());
        }
    }

    public ArrayList<Token<T>> tokenize(String text) throws TokenizerException {
        return tokenize(new ByteArrayInputStream(text.getBytes()));
    }



    private void doTokenize(InputStream input) throws TokenizerException {
        tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            this.reader = reader;
            StringBuilder currentTextBuilder = new StringBuilder();
            List<T> possibleTokenTypes = new LinkedList<>();

            char currentChar = (char)0;
            boolean skipNextReadChar = false;
            while ( true ) {
                if ( skipNextReadChar )
                    skipNextReadChar = false;
                else
                    currentChar = readChar();
                if ( currentChar==(char)-1)
                    break;
                currentTextBuilder.append(currentChar);
                final String currentText = currentTextBuilder.toString();

                // for debugging purposes:
                //if ( currentText.equals("/") )
                //    System.out.println("*");

                // 1. check StartsWith
                if ( currentText.length()==1 ) {
                    // started with the first character --> find all possible tokens
                    possibleTokenTypes = Arrays.stream(allTokenTypes).filter(t -> t.isValid(currentText).orElse(true) ).collect(Collectors.toCollection(LinkedList::new));
                    if (possibleTokenTypes.isEmpty())
                        throw new TokenizerNoPossibleTokenFoundException(currentText);
                }

                // 2. check valid chars
                var tokenTypesNowInvalid = possibleTokenTypes.stream().filter( t -> !t.isValid(currentText).orElse(true) ).collect(Collectors.toCollection(LinkedList::new));
                possibleTokenTypes.removeIf( t -> !t.isValid(currentText).orElse(true) );

                if ( possibleTokenTypes.isEmpty() ) {
                    if ( tokenTypesNowInvalid.isEmpty() )
                        throw new TokenizerNoPossibleTokenFoundException(currentText);
                    if ( tokenTypesNowInvalid.size()>1 )
                        tokenTypesNowInvalid.removeIf( t -> !t.isValidWithEnd(currentText).orElse(true) );
                    if ( tokenTypesNowInvalid.size()>1 )
                        throw new TokenizerMultipleTokensFoundException(currentText, List.copyOf(tokenTypesNowInvalid)) ;
                    // final token found --> store it in the results
                    addToken( tokenTypesNowInvalid.get(0), currentText.substring(0, currentText.length() - 1) );
                    currentTextBuilder = new StringBuilder();
                    skipNextReadChar = true;
                }

                // 3. check EndsWith
                if ( possibleTokenTypes.size()==1 ) {
                    if ( possibleTokenTypes.get(0).isValidWithEnd(currentText).orElse(false) )
                    {
                        // final token found --> store it in the results
                        addToken(possibleTokenTypes.get(0), currentText);
                        currentTextBuilder = new StringBuilder();
                    }
                }
            }

        } catch (IOException e) {
            throw new TokenizerException( e.getMessage() );
        } finally {
            this.reader = null;
        }
    }


    protected Token<T> addToken(T possibleTokenType, String currentText) {
        Token<T> token = new Token<>(possibleTokenType, currentText);
        tokens.add(token);
        return token;
    }

    private char readChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            return (char) -1;
        }
    }
}
