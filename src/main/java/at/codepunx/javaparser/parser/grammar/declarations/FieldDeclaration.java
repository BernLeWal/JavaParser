package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.expressions.Expression;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;
import lombok.Setter;

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

        visibility = new ModifierDeclaration<>(JavaLanguage.Visibility.class, reader).getValue();
        staticModifier = new ModifierDeclaration<>(JavaLanguage.Static.class, reader).getValue();
        finalModifier = new ModifierDeclaration<>(JavaLanguage.Final.class, reader).getValue();

        referenceTypeName = new ReferenceType(reader).getValue();
        setValue(mandatoryToken( reader, JavaTokenType.IDENTIFIER));
        if ( optionalToken( reader, JavaTokenType.OPERATOR, "=") ) {
            mandatory( reader, Expression::new );
        }
        mandatoryToken(reader, JavaTokenType.SEMIKOLON);

    }

    @Getter
    @Setter
    private JavaLanguage.Visibility visibility;
    private JavaLanguage.Static staticModifier;
    private JavaLanguage.Final finalModifier;

    @Getter
    @Setter
    private String referenceTypeName;

    public boolean isStatic() {
        return staticModifier != null;
    }
    public boolean isFinal() { return finalModifier != null; }


    @Override
    public int getChildCount() {
        return super.getChildCount()
                + (visibility!=null ? 1 : 0)
                + (staticModifier!=null ? 1 : 0)
                + (finalModifier!=null ? 1 : 0);
    }
}
