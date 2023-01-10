package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.comments.BlockComment;
import at.codepunx.javaparser.parser.grammar.comments.LineComment;
import at.codepunx.javaparser.parser.grammar.declarations.ImportDeclaration;
import at.codepunx.javaparser.parser.grammar.declarations.PackageDeclaration;
import at.codepunx.javaparser.parser.grammar.declarations.ClassDeclaration;
import at.codepunx.javaparser.tokenizer.TokenReader;
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
    public CompilationUnit(String javaFileName, TokenReader<JavaTokenType> reader) throws ParseException {
        setValue(javaFileName);

        multiple(reader, r -> {
            optional(r, LineComment::new);
            optional(r, BlockComment::new);
        });
        mandatory(reader, PackageDeclaration::new);
        multiple(reader, r -> {
            optional(r, LineComment::new);
            optional(r, BlockComment::new);
            optional(r, ImportDeclaration::new);
        });
        multiple(reader, r -> {
            optional(r, ClassDeclaration::new);
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
