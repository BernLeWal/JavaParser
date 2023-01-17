package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class ImportDeclaration extends Node {
    /*
    <import declaration> ::= <single type import declaration> | <type import on demand declaration>
    <single type import declaration> ::= import <type name> ;
    <type import on demand declaration> ::= import <package name> . * ;
     */
    public ImportDeclaration(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken( JavaTokenType.KEYWORD, "import");
        setValue(new ReferenceType(p).getValue());
        p.mandatoryToken( JavaTokenType.SEMIKOLON);
    }
}
