package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class FieldAccess extends Node {
    /*
        <field access> ::= <primary> '.' <identifier> | super '.' <identifier>
     */
    public FieldAccess(Parser<JavaTokenType> p) throws ParseException {
        super( p );
//        p.mandatoryOneOf(  
//                Primary::new,
//                r->{
//                    p.mandatoryToken( JavaTokenType.KEYWORD, "super");
//                    addChild( new Identifier("super") );
//                    return this;
//                }
//        ).sendTo(this::addChild);
//        p.mandatoryToken( JavaTokenType.DOT);
//        mandatory(reader, Identifier::new).sendTo(this::addChild);

        try {
            StringBuilder fqcn = new StringBuilder();
            if ( p.getReader().tryReadToken( JavaTokenType.KEYWORD, "this") )
                fqcn.append(p.getReader().readToken( JavaTokenType.KEYWORD, "this") );
            else if ( p.getReader().tryReadToken( JavaTokenType.KEYWORD, "super") )
                fqcn.append(p.getReader().readToken( JavaTokenType.KEYWORD, "super") );
            else
                fqcn.append(p.getReader().readToken(JavaTokenType.IDENTIFIER).getValue());
            while( p.getReader().tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(p.getReader().readToken(JavaTokenType.DOT).getValue());
                fqcn.append(p.getReader().readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
