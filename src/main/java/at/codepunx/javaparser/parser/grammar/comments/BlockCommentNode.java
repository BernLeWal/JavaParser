package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class BlockCommentNode extends Node implements CommentInterface {
    public BlockCommentNode(TokenReader<JavaTokenType> reader) throws ParseException {
        try {
            setValue( reader.readToken(JavaTokenType.COMMENT_BLOCK).getValue() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }
}
