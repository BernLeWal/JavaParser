package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.comments.BlockComment;
import at.codepunx.javaparser.parser.grammar.comments.LineComment;
import at.codepunx.javaparser.parser.grammar.declarations.ClassDeclaration;
import at.codepunx.javaparser.parser.grammar.declarations.ImportDeclaration;
import at.codepunx.javaparser.parser.grammar.declarations.PackageDeclaration;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.List;
import java.util.Optional;

public class CompilationUnit extends Node {
    /*
    <compilation unit> ::= <package declaration>? <import declarations>? <type declarations>?
    <import declarations> ::= <import declaration> | <import declarations> <import declaration>
    <type declarations> ::= <type declaration> | <type declarations> <type declaration>
    <type declaration> ::= <class declaration> | <interface declaration> | ;
     */
    public CompilationUnit(Parser<JavaTokenType> p, String javaFileName) throws ParseException {
        super( p );
        setValue(javaFileName);

        p.multiple(r -> {
            p.optional(LineComment::new).sendTo(this::addComment);
            p.optional(BlockComment::new).sendTo(this::addComment);
        });
        p.mandatory( PackageDeclaration::new).sendTo(this::addChild);
        p.multiple(r -> {
            p.optional(LineComment::new).sendTo(this::addComment);
            p.optional(BlockComment::new).sendTo(this::addComment);
            p.optional(ImportDeclaration::new).sendTo(this::addChild);
        });
        p.multiple(r -> {
            p.optional(ClassDeclaration::new).sendTo(this::addChild);
        });
    }

    String getJavaFileName() {
        return getValue();
    }
    Optional<PackageDeclaration> getPackage() {
        return getChild(PackageDeclaration.class);
    }
    List<ImportDeclaration> getImports() {
        return getChildren(ImportDeclaration.class);
    }

}
