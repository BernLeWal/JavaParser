package at.codepunx.javaparser.tokenizer;


import java.util.Optional;

public enum JavaTokenType {
    WHITESPACE(false, " \t\n\r", null, " \t\n\r"),

    CONST_STRING( true, "\"", "\""),
    CONST_STRING_MULTILINE( true, "\"\"\"", "\"\"\""),
    CONST_CHAR( true, "'", "'"),

    CONST_NUMBER( false, "0123456789", null, "0123456789.e-+"), // TODO . as valid first character
    CONST_LONG( false, "0123456789", "Ll", "0123456789"),
    CONST_FLOAT( false, "0123456789", "Ff", "0123456789.e-+"), // TODO . as valid first character

    NAME_OR_KEYWORD( false, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", null,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789"),

    COMMENT_LINE( true, "//", "\n"), // TODO only \r or \r\n
    COMMENT_BLOCK( true, "/*", "*/"),

    CODE_BLOCK_START("{"),
    CODE_BLOCK_END("}"),

    ROUND_BRAKET_START("("),
    ROUND_BRAKET_END(")"),
    SQUARE_BRAKET_START("["),
    SQUARE_BRAKET_END("]"),

    EXPR_ASSIGNMENT("="),
    EXPR_NOT("!"),
    EXPR_PLUS( "+"),
    EXPR_MINUS( "-"),
    EXPR_MUL( "*"),
    EXPR_DIV( "/"),
    EXPR_GREATER( ">"),
    EXPR_SMALLER( "<"),
    EXPR_LAMBDA( "->"),

    DOUBLE_POINT(":"),
    SEMIKOLON(";"),
    DOT("."),
    COMMA( ","),

    ANNOTATION("@")
    ;


    private final boolean exactMatch;
    private final String startsWith;
    private final String endsWith;  // null...all chars allowed (don't check the end); ""...no further chars allowed after startsWith
    private final String validChars; // null...all chars allowed (don't check validChars); ""...no further chars allowed after startsWith


    JavaTokenType(String startsWith) {
        this.startsWith = startsWith;
        this.exactMatch = true;
        this.endsWith = "";   //no further chars (than the first one) allowed
        this.validChars = "";   //no further chars (than the first one) allowed
    }

    JavaTokenType(boolean exactMatch, String startsWith, String endsWith) {
        this.exactMatch = exactMatch;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.validChars = null; // all chars allowed in between start and end
    }

    JavaTokenType(boolean exactMatch, String startsWith, String endsWith, String validChars) {
        this.exactMatch = exactMatch;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.validChars = validChars; // all chars allowed in between start and end
    }


    /**
     *
     * @param s current string
     * @return true...valid; false...invalid; empty...need more data
     */
    public Optional<Boolean> isValid(String s ) {
        if ( s==null )
            return Optional.empty();   // invalid value
        if ( s.equals("") )
            return Optional.empty();

        // check startWith
        if ( this.startsWith!=null ) {
            if (this.exactMatch) {
                if (s.length() <= this.startsWith.length())
                    if ( this.startsWith.startsWith(s) )
                        return Optional.empty();
                    else
                        return Optional.of(false);
                else if (!s.startsWith(this.startsWith))
                    return Optional.of( false );
                if ( s.length() >= this.startsWith.length() )
                    s = s.substring(this.startsWith.length());
            } else {
                if (this.startsWith.indexOf(s.substring(0, 1)) < 0)
                    return Optional.of(false);
                s = s.substring(1);
            }
        }

        // check validChars
        if ( validChars == null )
            return Optional.of(true);    // all chars are possible
        if ( validChars.equals("") )
            return Optional.of(s.equals(""));    // no further chars are allowed
        for( char c : s.toCharArray() ) {
            if ( validChars.indexOf(c)<0 )
                return Optional.of(false);
        }

        return Optional.of(true);
    }

    public Optional<Boolean> isValidWithEnd( String s ) {
        // check endsWith
        if ( this.endsWith==null )
            return Optional.empty();

        if ( this.startsWith!=null ) {
            if (this.exactMatch) {
                if (s.length() < this.startsWith.length() )
                    return Optional.empty();
                s = s.substring(this.startsWith.length());
            } else {
                if (s.length() < 1 )
                    return Optional.empty();
                s = s.substring(1);
            }
        }

        if (this.endsWith.equals("")) // no more chars allowed
            return Optional.of( s.equals("") );

        if (this.exactMatch) {
            if (s.length() == this.endsWith.length())
                return Optional.of( s.equals(this.endsWith) );
            else if (s.length() > this.endsWith.length()) {
                if (!s.endsWith(this.endsWith))
                    return Optional.of(false );
                s = s.substring(0, s.length() - this.endsWith.length());
            }
            else
                return Optional.empty();
        } else {
            if (s.length() == 1)
                return Optional.of( this.endsWith.indexOf(s.substring(0, 1)) >= 0 );
            else if (s.length() > 1) {
                if (this.endsWith.indexOf(s.substring(s.length() - 1)) < 0)
                    return Optional.of( false );
                s = s.substring(0, s.length() - 1);
            }
            else
                return Optional.empty();
        }

        // check validChars
        if ( validChars == null )
            return Optional.of(true);    // all chars are possible
        if ( validChars.equals("") )
            return Optional.of(s.equals(""));    // no further chars are allowed
        for( char c : s.toCharArray() ) {
            if ( validChars.indexOf(c)<0 )
                return Optional.of(false);
        }

        return Optional.of(true);
    }

}
