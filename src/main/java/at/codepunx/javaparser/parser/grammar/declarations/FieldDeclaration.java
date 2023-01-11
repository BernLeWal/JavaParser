package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.expressions.Expression;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class FieldDeclaration extends Node {
    /*
    <field declaration> ::= <field modifiers>? <type> <variable declarators> ;
    <field modifiers> ::= <field modifier> | <field modifiers> <field modifier>
    <field modifier> ::= public | protected | private | static | final | transient | volatile
    <variable declarators> ::= <variable declarator> | <variable declarators> , <variable declarator>
    <variable declarator> ::= <variable declarator id> | <variable declarator id> = <variable initializer>
    <variable declarator id> ::= <identifier> | <variable declarator id> [ ]
    <variable initializer> ::= <expression> | <array initializer>
     */
    public FieldDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        optional(reader, JavadocComment::new);

        setAttribute(JavaLanguage.Visibility.class.getSimpleName(), new ModifierDeclaration<>(JavaLanguage.Visibility.class, reader));
        setAttribute(JavaLanguage.Static.class.getSimpleName(), new ModifierDeclaration<>(JavaLanguage.Static.class, reader));
        setAttribute(JavaLanguage.Final.class.getSimpleName(), new ModifierDeclaration<>(JavaLanguage.Final.class, reader));

        setAttribute(ReferenceType.class.getSimpleName(), new ReferenceType(reader));
        setValue(mandatoryToken(reader, JavaTokenType.IDENTIFIER));
        if (optionalToken(reader, JavaTokenType.OPERATOR, "=")) {
            mandatory(reader, Expression::new);
        }
        mandatoryToken(reader, JavaTokenType.SEMIKOLON);

    }


    public JavaLanguage.Visibility getVisibility() { return getAttributeValue(JavaLanguage.Visibility.class); }
    public boolean isStatic() { return getAttributeValue(JavaLanguage.Static.class) != null; }
    public boolean isFinal() {
        return getAttributeValue(JavaLanguage.Final.class) != null;
    }
    public String getReferenceType() { return getAttributeValue(ReferenceType.class).getValue(); }
    public String getName() { return getValue(); }
}
