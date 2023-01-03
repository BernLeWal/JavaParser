package at.codepunx.javaparser.tokenizer;

public enum JavaTokenType {
    WHITESPACE(" \t\n\r", " \t\n\r", null),

    CONST_STRING("\"", null, "\""),
    // TODO CONST_STRING_MULTILINE
    CONST_CHAR("'", null, "'"),
    CONST_INT( "0123456789", "0123456789"),
    CONST_LONG( "0123456789", "0123456789", "Ll"),
    CONST_DOUBLE( "0123456789.", "0123456789."),
    CONST_FLOAT( "0123456789.", "0123456789.e-+", "Ff"),

    NAME_OR_KEYWORD( "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789"),

    COMMENT_LINE( "/", null, "\n\r"), // TODO //
    COMMENT_BLOCK( "/", null, "/"), // TODO /* ... */

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

    DOUBLE_POINT(":"),
    SEMIKOLON(";"),
    DOT("."),
    COMMA( ","),

    ANNOTATION("@")
    ;

    private final String firstCharIn;
    private final String validCharIn;
    private final String lastCharIn;

    JavaTokenType(String firstCharIn, String validCharIn, String lastCharIn) {
        this.firstCharIn = firstCharIn;
        this.validCharIn = validCharIn;
        this.lastCharIn = lastCharIn;
    }

    JavaTokenType(String firstCharIn, String validCharIn) {
        this.firstCharIn = firstCharIn;
        this.validCharIn = validCharIn;
        this.lastCharIn = null;
    }

    JavaTokenType(String firstCharIn) {
        this.firstCharIn = firstCharIn;
        this.validCharIn = "";  //no further chars (than the first one) allowed
        this.lastCharIn = firstCharIn;
    }


    public boolean isFirstChar(char c) {
        if ( firstCharIn == null )
            return true;    // all chars are possible
        return firstCharIn.indexOf(c)>=0;
    }

    public boolean isValidChar(char c) {
        if ( validCharIn == null )
            return true;    // all chars are possible
        return validCharIn.indexOf(c)>=0;
    }

    public boolean isLastChar(char c) {
        if ( lastCharIn == null )
            return false;   // token has no last-char
        return lastCharIn.indexOf(c)>=0;
    }

    public boolean hasLastChar() {
        return lastCharIn != null;
    }
}
