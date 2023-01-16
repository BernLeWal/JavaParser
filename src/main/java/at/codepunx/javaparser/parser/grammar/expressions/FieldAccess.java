package at.codepunx.javaparser.parser.grammar.expressions;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.types.Identifier;
import at.codepunx.javaparser.parser.grammar.types.Literal;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import static at.codepunx.javaparser.parser.Parser.*;

public class FieldAccess extends Node {
    /*
        <field access> ::= <primary> '.' <identifier> | super '.' <identifier>
     */
    public FieldAccess(TokenReader<JavaTokenType> reader) throws ParseException {
//        mandatoryOneOf( reader,
//                Primary::new,
//                r->{
//                    mandatoryToken(r, JavaTokenType.KEYWORD, "super");
//                    addChild( new Identifier("super") );
//                    return null;
//                }
//        ).sendTo(this::addChild);
//        mandatoryToken(reader, JavaTokenType.DOT);
//        mandatory(reader, Identifier::new).sendTo(this::addChild);

        try {
            StringBuilder fqcn = new StringBuilder();
            if ( reader.tryReadToken( JavaTokenType.KEYWORD, "this") )
                fqcn.append(reader.readToken( JavaTokenType.KEYWORD, "this") );
            else if ( reader.tryReadToken( JavaTokenType.KEYWORD, "super") )
                fqcn.append(reader.readToken( JavaTokenType.KEYWORD, "super") );
            else
                fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            while( reader.tryReadToken(JavaTokenType.DOT) ) {
                fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
                fqcn.append(reader.readToken(JavaTokenType.IDENTIFIER).getValue());
            }
            setValue( fqcn.toString() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
