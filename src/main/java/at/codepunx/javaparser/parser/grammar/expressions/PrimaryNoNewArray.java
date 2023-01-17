package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.Literal;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

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
    public PrimaryNoNewArray(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        System.out.println(toString());
        p.mandatoryOneOf(  
                Literal::new,
                r->{
                    p.mandatoryToken( JavaTokenType.KEYWORD, "this").sendTo(this::setValue);
                    return this;
                },
                r->{
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_OPEN);
                    p.mandatory( Expression::new ).sendTo(this::addChild);
                    p.mandatoryToken( JavaTokenType.ROUND_BRACKET_CLOSE);
                    return this;
                },
                ClassInstanceCreationExpression::new,
                FieldAccess::new,
                MethodInvocation::new,
                ArrayAccess::new
        ).sendTo(this::addChild);
    }
}
