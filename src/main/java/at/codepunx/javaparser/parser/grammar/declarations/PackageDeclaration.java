package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class PackageDeclaration extends Node {
    /*
    <package declaration> ::= package <package name> ;
    <package name> ::= <identifier> | <package name> . <identifier>
     */
    public PackageDeclaration(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.KEYWORD, "package");
        setValue(new ReferenceType(p).getValue());
        p.mandatoryToken( JavaTokenType.SEMIKOLON );
    }
}
