package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.comments.JavadocComment;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class MethodDeclaration extends Node {
    /*
    <method declaration> ::= <method header> <method body>
    <method header> ::= <method modifiers>? <result type> <method declarator> <throws>?
    <result type> ::= <type> | void
    <method modifiers> ::= <method modifier> | <method modifiers> <method modifier>
    <method modifier> ::= public | protected | private | static | abstract | final | synchronized | native
    <method declarator> ::= <identifier> ( <formal parameter list>? )
    <method body> ::= <block> | ;
     */

    public MethodDeclaration(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.optional(JavadocComment::new).sendTo(this::addComment);
    }
}
