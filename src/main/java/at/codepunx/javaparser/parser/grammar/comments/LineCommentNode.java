package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class LineCommentNode extends Node implements CommentInterface {
    public LineCommentNode(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            setValue( reader.readToken(JavaTokenType.COMMENT_LINE).getValue() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
