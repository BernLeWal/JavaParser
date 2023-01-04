package at.codepunx.javaparser.tokenizer;

import java.util.Arrays;
import java.util.Optional;

public class TokenizerValidators {
    public static Optional<Boolean> is(final String reference, final String current) {
        if ( reference==null )
            return Optional.of(false);  // invalid reference    // TODO true?
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        if ( current.length() < reference.length() )
            return ( reference.startsWith(current) ) ? Optional.empty() : Optional.of(false);
        else if (current.length() > reference.length() )
            return Optional.of(false);
        else
            return Optional.of( reference.equals(current) );
    }

    public static Optional<Boolean> isIn(final String allowedChars, final String current) {
        if ( allowedChars==null || allowedChars.isEmpty() )
            return Optional.of(false);
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        if ( current.length() > 1 )
            return Optional.of(false );
        else
            return Optional.of( allowedChars.indexOf( current.toCharArray()[0])>=0 );
    }

    public static Optional<Boolean> encapsulatedIn(final String prefix, final String postfix, final String current) {
        if ( prefix==null )
            return Optional.of(false);  // invalid prefix
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        if ( current.length() < prefix.length() )
            return ( prefix.startsWith(current) ) ? Optional.empty() : Optional.of(false);
        else if (!current.startsWith(prefix) )
            return Optional.of(false);
        else if (current.length() >= prefix.length() ) {
            if (postfix==null)      // TODO should this be allowed?
                return Optional.of(true);
            return ( current.substring(prefix.length()).endsWith( postfix )) ? Optional.of(true) : Optional.empty();
        }

        return Optional.empty(); // TODO true?
    }

    public static Optional<Boolean> charsIn(final String allowedChars, final String current) {
        if ( allowedChars==null || allowedChars.isEmpty() )
            return Optional.of(false);
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        for (char c : current.toCharArray() ) {
            if (allowedChars.indexOf(c)<0 )
                return Optional.of(false);
        }
        return Optional.empty();    // there may be more input
    }

    public static Optional<Boolean> charsIn(final String allowedFirstChars, final String allowedFurtherChars, final String current) {
        if ( allowedFirstChars==null || allowedFirstChars.isEmpty() )
            return Optional.of(false);
        if ( allowedFurtherChars==null || allowedFurtherChars.isEmpty() )
            return Optional.of(false);
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        boolean isFirst = true;
        for (char c : current.toCharArray() ) {
            if ( ( isFirst ? allowedFirstChars : allowedFurtherChars).indexOf(c)<0 )
                return Optional.of(false);
            isFirst = false;
        }
        return Optional.empty();    // there may be more input
    }

    public static Optional<Boolean> inList(final String[] list, final String current) {
        if ( list==null )
            return Optional.of(false);
        if ( current==null || current.isEmpty() )
            return Optional.empty();

        if (Arrays.asList(list).contains(current))
            return Optional.of( true );
        if (Arrays.stream(list).anyMatch(s -> s.startsWith(current) ))
            return Optional.empty();
        return Optional.of(false);
    }
}
