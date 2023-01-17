package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.BlockComment;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.parser.grammar.comments.LineComment;
import at.codepunx.javaparser.parser.impl.JavaLanguage;
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
    public ClassDeclaration(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.optional( JavadocComment::new).sendTo(this::addComment);

        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PUBLIC)).sendTo(n -> setAttribute(n.getValue(), n));
        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PROTECTED)).sendTo(n -> setAttribute(n.getValue(), n));
        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Visibility.PRIVATE)).sendTo(n -> setAttribute(n.getValue(), n));

        p.optional( p1 -> new ModifierDeclaration(p, JavaLanguage.Abstract.ABSTRACT)).sendTo( n->setAttribute(n.getValue(), n));

        p.mandatoryToken( JavaTokenType.KEYWORD, "class");
        p.mandatoryToken( JavaTokenType.IDENTIFIER).sendTo(this::setValue);

        p.optional( ExtendsDeclaration::new).sendTo(this::addChild);
        if (!p.optional( ImplementsDeclaration::new).isEmpty()) {
            p.multiple( p1 -> {
                p.optional(p2 -> new ImplementsDeclaration(p, false)).sendTo(this::addChild);
            });
        }

        p.mandatoryToken(JavaTokenType.CODE_BLOCK_START);

        p.multiple( r -> {
            p.optional( LineComment::new).sendTo(this::addComment);
            p.optional( BlockComment::new).sendTo(this::addComment);
            p.optional( FieldDeclaration::new).sendTo(this::addChild);
//            p.optional( MethodDeclaration::new).sendTo(this::addChild);
        });

//        p.mandatoryToken(JavaTokenType.CODE_BLOCK_END);
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


    public boolean isPublic() { return hasAttribute(JavaLanguage.Visibility.PUBLIC.value()); }
    public boolean isProtected() { return hasAttribute(JavaLanguage.Visibility.PROTECTED.value()); }
    public boolean isPrivate() { return hasAttribute(JavaLanguage.Visibility.PRIVATE.value()); }
    public boolean isAbstract() { return hasAttribute(JavaLanguage.Abstract.ABSTRACT.value()); }
    public String getName() { return getValue(); }
}
