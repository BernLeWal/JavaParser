package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.BlockComment;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.comments.LineComment;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclaration extends Node {

    /*
    <class declaration> ::= <class modifiers>? class <identifier> <super>? <interfaces>? <class body>
    <class modifiers> ::= <class modifier> | <class modifiers> <class modifier>
    <class modifier> ::= public | abstract | final
    <super> ::= extends <class type>
    <interfaces> ::= implements <interface type list>
    <interface type list> ::= <interface type> | <interface type list> , <interface type>
    <class body> ::= { <class body declarations>? }
    <class body declarations> ::= <class body declaration> | <class body declarations> <class body declaration>
    <class body declaration> ::= <class member declaration> | <static initializer> | <constructor declaration>
    */
    public ClassDeclaration(TokenReader<JavaTokenType> reader) throws ParseException {
        optional(reader, JavadocComment::new);
        visibility = new ModifierDeclaration<>(JavaLanguage.Visibility.class, reader).getValue();
        abstractModifier = new ModifierDeclaration<>(JavaLanguage.Abstract.class, reader).getValue();

        try {
            reader.readToken(JavaTokenType.KEYWORD, "class");
            setValue(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }

        optional(reader, ExtendsDeclaration::new);
        if (optional(reader, ImplementsDeclaration::new)) {
            multiple(reader, r -> {
                if (!optional(reader, r1 -> new ImplementsDeclaration(false, r1)))
                    throw new ParseException("No more interfaces implemented");
            });
        }

        try {
            reader.readToken(JavaTokenType.CODE_BLOCK_START);
        } catch (TokenReaderException e) {
            System.err.println(e);
            throw new ParseException(e);
        }

        multiple(reader, r -> {
            optional(r, LineComment::new);
            optional(r, BlockComment::new);
            optional(r, FieldDeclaration::new);
//            optional(r, MethodDeclaration::new);
        });

//        try {
//            reader.readToken(JavaTokenType.CODE_BLOCK_END );
//        } catch (TokenReaderException e) {
//            System.err.println(e.toString());
//            throw new ParseException(e);
//        }
    }

    @Override
    public void addChild(Node node) {
        if (node == null)
            return;

        if (node instanceof ExtendsDeclaration baseClass)
            this.baseClass = baseClass;
        else if (node instanceof ImplementsDeclaration baseInterface)
            this.baseInterfaces.add(baseInterface);
        else
            super.addChild(node);
    }


    @Getter
    @Setter
    private JavaLanguage.Visibility visibility;

    private JavaLanguage.Abstract abstractModifier;

    @Getter
    private ExtendsDeclaration baseClass;
    @Getter
    private final List<ImplementsDeclaration> baseInterfaces = new ArrayList<>();


    public String getName() {
        return getValue();
    }

    public boolean isAbstract() {
        return abstractModifier != null;
    }

    public void setAbstract(boolean value) {
        abstractModifier = value ? JavaLanguage.Abstract.ABSTRACT : null;
    }
}
