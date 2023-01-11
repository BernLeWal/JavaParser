package at.codepunx.javaparser.parser;

import at.codepunx.javaparser.tokenizer.TokenReaderException;

public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
//        System.err.println( "ParseException: " + message);
    }

    public ParseException(TokenReaderException e) {
        super(e);
//        System.err.println( "ParseException: " + e.getMessage());
    }
}
