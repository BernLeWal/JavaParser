package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.expressions.Expression;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

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
        optional(reader, JavadocComment::new).sendTo(this::addComment);

        optional(reader, r -> new ModifierDeclaration(JavaLanguage.Visibility.PUBLIC, reader)).sendTo(n -> setAttribute(n.getValue(), n));
        optional(reader, r -> new ModifierDeclaration(JavaLanguage.Visibility.PROTECTED, reader)).sendTo(n -> setAttribute(n.getValue(), n));
        optional(reader, r -> new ModifierDeclaration(JavaLanguage.Visibility.PRIVATE, reader)).sendTo(n -> setAttribute(n.getValue(), n));

        optional(reader, r->new ModifierDeclaration(JavaLanguage.Static.STATIC, reader)).sendTo( n->setAttribute(n.getValue(), n));
        optional(reader, r->new ModifierDeclaration(JavaLanguage.Final.FINAL, reader)).sendTo( n->setAttribute(n.getValue(), n));

        mandatory(reader, ReferenceType::new).sendTo( n->setAttribute(n.getClass().getSimpleName(), n));
        mandatoryToken(reader, JavaTokenType.IDENTIFIER).sendTo(this::setValue);

        if (optionalToken(reader, JavaTokenType.OPERATOR, "=")) {
            mandatory(reader, Expression::new).sendTo(this::addChild);
        }
        mandatoryToken(reader, JavaTokenType.SEMIKOLON);

    }


    public boolean isPublic() { return hasAttribute(JavaLanguage.Visibility.PUBLIC.value()); }
    public boolean isProtected() { return hasAttribute(JavaLanguage.Visibility.PROTECTED.value()); }
    public boolean isPrivate() { return hasAttribute(JavaLanguage.Visibility.PRIVATE.value()); }
    public boolean isStatic() { return hasAttribute(JavaLanguage.Static.STATIC.value()); }
    public boolean isFinal() { return hasAttribute(JavaLanguage.Final.FINAL.value()); }
    public String getReferenceType() { return getAttributeValue(ReferenceType.class).getValue(); }
    public String getName() { return getValue(); }
}
