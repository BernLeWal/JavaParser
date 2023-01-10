package at.codepunx.javaparser.tokenizer.impl;


import at.codepunx.javaparser.tokenizer.TokenTypeInterface;
import at.codepunx.javaparser.tokenizer.TokenizerValidators;

import java.util.Optional;
import java.util.function.Function;

public enum JavaTokenType implements TokenTypeInterface {
    WHITESPACE(s -> TokenizerValidators.charsIn(" \t\n\r", s)),

    CONST_STRING(s -> TokenizerValidators.encapsulatedIn("\"", "\"", s)),
    CONST_STRING_MULTILINE( s -> TokenizerValidators.encapsulatedIn("\"\"\"", "\"\"\"", s)),
    CONST_CHAR( s -> TokenizerValidators.encapsulatedIn( "'", "'",s)),

    COMMENT_JAVADOC( s -> TokenizerValidators.encapsulatedIn("/**", "*/", s )),
    COMMENT_LINE( s -> TokenizerValidators.encapsulatedIn( "//", "\n",s)), // TODO only \r or \r\n
    COMMENT_BLOCK( s -> TokenizerValidators.encapsulatedIn("/*", "*/",s)),

    CODE_BLOCK_START(s -> TokenizerValidators.is("{", s)),
    CODE_BLOCK_END(s -> TokenizerValidators.is("}", s)),
    ROUND_BRACKET_START(s -> TokenizerValidators.is("(", s)),
    ROUND_BRACKET_END(s -> TokenizerValidators.is(")", s)),
    SQUARE_BRACKET_START(s -> TokenizerValidators.is("[", s)),
    SQUARE_BRACKET_END(s -> TokenizerValidators.is("]", s)),

    OPERATOR( s -> TokenizerValidators.isIn("+-*/%<>=!&|", s)),
    LAMBDA(s -> TokenizerValidators.is( "->", s)),

    DOUBLE_POINT(s -> TokenizerValidators.is(":", s)),
    SEMIKOLON(s -> TokenizerValidators.is(";", s)),
    DOT(s -> TokenizerValidators.is(".", s)),
    COMMA(s -> TokenizerValidators.is( ",", s)),

    ANNOTATION(s -> TokenizerValidators.is("@", s)),

    KEYWORD( s->TokenizerValidators.inList( JavaLanguage.KEYWORDS, s )),
    PRIMITIVE( s->TokenizerValidators.inList( JavaLanguage.PRIMITIVES, s)),
    IDENTIFIER(s -> TokenizerValidators.charsIn( "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789", s)),

    CONST_NUMBER( s -> TokenizerValidators.charsIn("0123456789", "0123456789.e-+", s)  ), // TODO . as valid first character
//    CONST_LONG( false, "0123456789", "Ll", "0123456789"),
//    CONST_FLOAT( false, "0123456789", "Ff", "0123456789.e-+"), // TODO . as valid first character

    ;

    private final Function<String, Optional<Boolean>> validatorFunc;

    JavaTokenType(Function<String, Optional<Boolean>> validatorFunc) {
        this.validatorFunc = validatorFunc;
    }

    @Override
    public Optional<Boolean> isValid(String s) {
        return validatorFunc.apply(s);
    }
}
