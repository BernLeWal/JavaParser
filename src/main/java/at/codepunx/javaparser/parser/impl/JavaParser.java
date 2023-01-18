package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.CompilationUnit;
import at.codepunx.javaparser.tokenizer.Token;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JavaParser extends Parser<JavaTokenType> {

    @Getter
    private final JavaTokenReader reader;

    public JavaParser(List<Token<JavaTokenType>> tokens) {
        this.reader = new JavaTokenReader(tokens);
        this.reader.setWhitespaceTokenTypes( new JavaTokenType[]{JavaTokenType.WHITESPACE} );
    }


    public CompilationUnit parseJavaFile(String javaFileName) throws ParseException {
        if ( !reader.hasNext() )
            throw new ParseException("No tokens to parse!");

        try {
            CompilationUnit compilationUnit = new CompilationUnit(this, javaFileName);
            compilationUnit.simplify();
            return compilationUnit;
        } catch (ParseException e) {
            log.error( e.toString() );
            throw new ParseException(e.getMessage());
        }
    }

}
