package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.BlockComment;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.comments.LineComment;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static at.codepunx.javaparser.parser.Parser.*;

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
        optional(reader, JavadocComment::new).sendTo(this::addComment);

        //optional(reader, r -> { return new ModifierDeclaration<>(JavaLanguage.Visibility.class, reader); } );
        setAttribute(JavaLanguage.Visibility.class.getSimpleName(), new ModifierDeclaration<>(JavaLanguage.Visibility.class, reader));
        setAttribute(JavaLanguage.Abstract.class.getSimpleName(), new ModifierDeclaration<>(JavaLanguage.Abstract.class, reader));

        mandatoryToken( reader, JavaTokenType.KEYWORD, "class");
        mandatoryToken( reader, JavaTokenType.IDENTIFIER).sendTo(this::setValue);

        optional(reader, ExtendsDeclaration::new).sendTo(this::addChild);
        if (optional(reader, ImplementsDeclaration::new).hasNode()) {
            multiple(this, reader, r -> {
                optional(reader, r1 -> new ImplementsDeclaration(false, r1)).sendTo(this::addChild);
            });
        }

        mandatoryToken(reader, JavaTokenType.CODE_BLOCK_START);

        multiple(this, reader, r -> {
            optional(r, LineComment::new).sendTo(this::addComment);
            optional(r, BlockComment::new).sendTo(this::addComment);
            optional(r, FieldDeclaration::new).sendTo(this::addChild);
//            optional(r, MethodDeclaration::new).sendTo(this::addChild);
        });

//        mandatoryToken(reader, JavaTokenType.CODE_BLOCK_END);
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
    private ExtendsDeclaration baseClass;
    @Getter
    private final List<ImplementsDeclaration> baseInterfaces = new ArrayList<>();


    public JavaLanguage.Visibility getVisibility() { return getAttributeValue(JavaLanguage.Visibility.class); }
    public boolean isAbstract() {
        return getAttributeValue(JavaLanguage.Abstract.class) != null;
    }
    public String getName() {
        return getValue();
    }
}
