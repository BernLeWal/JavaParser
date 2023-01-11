package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class LineComment extends Node implements CommentInterface {
    public LineComment(TokenReader<JavaTokenType> reader) throws ParseException {
        setValue( mandatoryToken( reader, JavaTokenType.COMMENT_LINE) );
    }
}
