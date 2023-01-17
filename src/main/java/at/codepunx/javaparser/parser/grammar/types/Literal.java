package at.codepunx.javaparser.parser.grammar.types;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class Literal extends Node {
    /*
        <literal> ::= <integer literal> | <floating-point literal> | <boolean literal> | <character literal> | <string literal> | <null literal>

        <integer literal> ::= <decimal integer literal> | <hex integer literal> | <octal integer literal>
        <decimal integer literal> ::= <decimal numeral> <integer type suffix>?
        <hex integer literal> ::= <hex numeral> <integer type suffix>?
        <octal integer literal> ::= <octal numeral> <integer type suffix>?
        <integer type suffix> ::= l | L
        <decimal numeral> ::= 0 | <non zero digit> <digits>?
        <digits> ::= <digit> | <digits> <digit>
        <digit> ::= 0 | <non zero digit>
        <non zero digit> ::= 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
        <hex numeral> ::= 0 x <hex digit> | 0 X <hex digit> | <hex numeral> <hex digit>
        <hex digit> :: = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | a | b | c | d | e | f | A | B | C | D | E | F
        <octal numeral> ::= 0 <octal digit> | <octal numeral> <octal digit>
        <octal digit> ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7
        <floating-point literal> ::= <digits> . <digits>? <exponent part>? <float type suffix>?
        <digits> <exponent part>? <float type suffix>?
        <exponent part> ::= <exponent indicator> <signed integer>
        <exponent indicator> ::= e | E
        <signed integer> ::= <sign>? <digits>
        <sign> ::= + | -
        <float type suffix> ::= f | F | d | D
        <boolean literal> ::= true | false
        <character literal> ::= ' <single character> ' | ' <escape sequence> '
        <single character> ::= <input character> except ' and \
        <string literal> ::= " <string characters>?"
        <string characters> ::= <string character> | <string characters> <string character>
        <string character> ::= <input character> except " and \ | <escape character>
        <null literal> ::= null
     */
    public Literal(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryOneOf(
                p1->{ p.mandatoryToken( JavaTokenType.CONST_NUMBER ).sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.PRIMITIVE, "true").sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.PRIMITIVE, "false").sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.CONST_CHAR  ).sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.CONST_STRING ).sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.CONST_STRING_MULTILINE ).sendTo(this::setValue); return this; },
                p1->{ p.mandatoryToken( JavaTokenType.PRIMITIVE, "null").sendTo(this::setValue); return this; }
        );
    }
}
