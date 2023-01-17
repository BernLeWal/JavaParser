package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.expressions.Expression;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
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
    public FieldDeclaration(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.optional( JavadocComment::new).sendTo(this::addComment);

        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PUBLIC)).sendTo(n -> setAttribute(n.getValue(), n));
        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PROTECTED)).sendTo(n -> setAttribute(n.getValue(), n));
        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PRIVATE)).sendTo(n -> setAttribute(n.getValue(), n));

        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Static.STATIC)).sendTo( n->setAttribute(n.getValue(), n));
        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Final.FINAL)).sendTo( n->setAttribute(n.getValue(), n));

        p.mandatory( ReferenceType::new).sendTo( n->setAttribute(n.getClass().getSimpleName(), n));
        p.mandatoryToken( JavaTokenType.IDENTIFIER ).sendTo(this::setValue);

        if (p.optionalToken( JavaTokenType.ASSIGNMENT, "=")) {
            p.mandatory( Expression::new ).sendTo(this::addChild);
        }
        p.mandatoryToken( JavaTokenType.SEMIKOLON );

    }


    public boolean isPublic() { return hasAttribute(JavaLanguage.Visibility.PUBLIC.value()); }
    public boolean isProtected() { return hasAttribute(JavaLanguage.Visibility.PROTECTED.value()); }
    public boolean isPrivate() { return hasAttribute(JavaLanguage.Visibility.PRIVATE.value()); }
    public boolean isStatic() { return hasAttribute(JavaLanguage.Static.STATIC.value()); }
    public boolean isFinal() { return hasAttribute(JavaLanguage.Final.FINAL.value()); }
    public String getReferenceType() { return getAttributeValue(ReferenceType.class).getValue(); }
    public String getName() { return getValue(); }
}
