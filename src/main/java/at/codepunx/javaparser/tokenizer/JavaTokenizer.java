package at.codepunx.javaparser.tokenizer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

@Slf4j
public class JavaTokenizer {

    // input
    @Getter
    private final JavaTokenType[] allTokenTypes = JavaTokenType.values();

    // output
    @Getter
    private ArrayList<JavaToken> tokens = new ArrayList<>();

    // intermediate variables during tokenizing
    private BufferedReader reader;


    public ArrayList<JavaToken> tokenize(InputStream input) throws TokenizerException {
        doTokenize(input);
        return tokens;
    }

    public ArrayList<JavaToken> tokenize(URI uri) throws TokenizerException {
        try {
            return tokenize(uri.toURL().openStream());
        } catch (IOException e) {
            throw new TokenizerException(e.getMessage());
        }
    }

    public ArrayList<JavaToken> tokenize(String text) throws TokenizerException {
        return tokenize(new ByteArrayInputStream(text.getBytes()));
    }

    private void doTokenize(InputStream input) throws TokenizerException {
        tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            this.reader = reader;
            StringBuilder currentText = new StringBuilder();
            LinkedList<JavaTokenType> possibleTokenTypes = new LinkedList<>();

            char currentChar = (char)0;
            boolean skipNextReadChar = false;
            while ( true ) {
                if ( skipNextReadChar )
                    skipNextReadChar = false;
                else
                    currentChar = readChar();
                if ( currentChar==(char)-1)
                    break;
                currentText.append(currentChar);

                // for debugging purposes:
                //if ( currentText.toString().equals("0:") )
                //    System.out.println("*");

                if ( possibleTokenTypes.isEmpty() ) {
                    // find possible tokens
                    for (JavaTokenType possibleTokenType : allTokenTypes) {
                        if (possibleTokenType.isFirstChar(currentChar))
                            possibleTokenTypes.add(possibleTokenType);
                    }
                    if (possibleTokenTypes.isEmpty())
                        throw new TokenizerNoPossibleTokenFoundException(currentText.toString());
                } else {
                    // check if one of possible tokens already done
                    for (JavaTokenType possibleTokenType : possibleTokenTypes ) {
                        if (possibleTokenType.isLastChar(currentChar)) {
                            // final token found --> store it in the results
                            addToken(possibleTokenType, currentText.toString());
                            currentText = new StringBuilder();
                            possibleTokenTypes = new LinkedList<>();
                            break;
                        }
                    }

                    if ( possibleTokenTypes.size()>1 ) {
                        // check which possible tokens to take out due to invalid further chars
                        for (int i = 0; i< possibleTokenTypes.size(); i++) {
                            JavaTokenType possibleTokenType = possibleTokenTypes.get(i);
                            if ( !possibleTokenType.isValidChar(currentChar) && possibleTokenType.hasLastChar() ) {
                                possibleTokenTypes.remove(i--); // take out, because char is invalid
                            }
                        }
                        if (possibleTokenTypes.isEmpty())
                            throw new TokenizerNoPossibleTokenFoundException(currentText.toString());
                    }

                    if ( possibleTokenTypes.size()>=1 ) {
                        // read the current token until the end
//                        JavaTokenType currentTokenType = possibleTokenTypes.getFirst();
                        for (int i = 0; i< possibleTokenTypes.size(); i++) {
                            JavaTokenType currentTokenType = possibleTokenTypes.get(i);
                            if (!currentTokenType.isValidChar(currentChar)) {
                                // current token type ends here --> store it in the results
                                addToken(currentTokenType, currentText.substring(0, currentText.length() - 1));
                                currentText = new StringBuilder();
                                skipNextReadChar = true;
                                possibleTokenTypes = new LinkedList<>();
                                break;
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            log.error("Failed to read from file! " + e);
        } finally {
            this.reader = null;
        }
    }

    private void addToken(JavaTokenType possibleTokenType, String currentText) {
        if ( possibleTokenType.equals(JavaTokenType.WHITESPACE) && currentText.equals(" "))
            return; // skip extra tokens for the single space char here
        tokens.add(new JavaToken(possibleTokenType, currentText));
    }


    private char readChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            return (char) -1;
        }
    }
}
