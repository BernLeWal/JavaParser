package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.comments.BlockCommentNode;
import at.codepunx.javaparser.parser.grammar.comments.LineCommentNode;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

import java.util.List;
import java.util.Optional;

public class JavaFileNode extends Node {
    public JavaFileNode(String javaFileName, TokenReader<JavaTokenType> reader) throws ParseException {
        setValue(javaFileName);

        multiple(reader, r -> {
            optional(r, LineCommentNode::new);
            optional(r, BlockCommentNode::new);
        });
        mandatory(reader, PackageNode::new);
        multiple(reader, r -> {
            optional(r, LineCommentNode::new);
            optional(r, BlockCommentNode::new);
            optional(r, ImportNode::new);
        });
    }

    String getJavaFileName() {
        return getValue();
    }

    Optional<PackageNode> getPackage() {
        return getChild(PackageNode.class);
    }

    List<ImportNode> getImports() {
        return getChildren(ImportNode.class);
    }

}
