package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.Literal;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class PrimaryNoNewArray extends Node {
    /*
    <primary no new array> ::= <literal>
                            | this
                            | ( <expression> )
                            | <class instance creation expression>
                            | <field access>
                            | <method invocation>
                            | <array access>
     */
    public PrimaryNoNewArray(TokenReader<JavaTokenType> reader) throws ParseException {
        mandatoryOneOf( reader,
                Literal::new,
                r->{
                    mandatoryToken(r, JavaTokenType.KEYWORD, "this").sendTo(this::setValue);
                    return null;
                },
                r->{
                    mandatoryToken(r, JavaTokenType.ROUND_BRACKET_OPEN);
                    mandatory(r, Expression::new ).sendTo(this::addChild);
                    mandatoryToken(r, JavaTokenType.ROUND_BRACKET_CLOSE);
                    return null;
                },
                ClassInstanceCreationExpression::new,
                FieldAccess::new,
                MethodInvocation::new,
                ArrayAccess::new
        ).sendTo(this::addChild);
    }
}
