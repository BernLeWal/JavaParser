package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.CompilationUnit;
import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.List;

public class JavaParser {


    public CompilationUnit parseJavaFile(String javaFileName, List<Token<JavaTokenType>> tokens) throws ParseException {
        if ( tokens==null || tokens.isEmpty() )
            return null;

        var reader = new TokenReader<>(tokens);
        reader.setWhitespaceTokenTypes( new JavaTokenType[]{JavaTokenType.WHITESPACE} );

        try {
            return new CompilationUnit(javaFileName, reader);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
