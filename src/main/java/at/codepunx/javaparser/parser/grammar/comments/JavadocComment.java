package at.codepunx.javaparser.parser.grammar.comments;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.Parser;
import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class JavadocComment extends Node implements CommentInterface {
    public JavadocComment(Parser<JavaTokenType> p) throws ParseException {
        super( p );
        p.mandatoryToken(JavaTokenType.COMMENT_JAVADOC).sendTo(this::setValue);
    }
}
