package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class JavadocComment extends Node implements CommentInterface {
    public JavadocComment(TokenReader<JavaTokenType> reader) throws ParseException {
        setValue( mandatoryToken( reader, JavaTokenType.COMMENT_JAVADOC) );
    }
}
