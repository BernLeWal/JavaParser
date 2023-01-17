package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class BlockComment extends Node implements CommentInterface {
    public BlockComment(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken(JavaTokenType.COMMENT_BLOCK).sendTo(this::setValue);
    }
}
