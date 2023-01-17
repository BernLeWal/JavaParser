package at.codepunx.javaparser.parser.grammar.declarations;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.ReferenceType;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;


public class ImplementsDeclaration extends Node {
    public ImplementsDeclaration(Parser<JavaTokenType> p, boolean readFirst) throws ParseException {
        super( p );
        if ( readFirst )
            p.mandatoryToken( JavaTokenType.KEYWORD, "implements");
        else // read next
            p.mandatoryToken( JavaTokenType.COMMA);
        setValue(new ReferenceType(p).getValue());
    }

    public ImplementsDeclaration( Parser<JavaTokenType> p ) throws ParseException {
        this(p, true);
    }
}
