package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.Node;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JavaRootNode extends Node {
    private final String javaFileName;

    private List<String> commentsList = new ArrayList<>();
    private JavaPackageNode javaPackage = null;
    private List<JavaImportNode> importList = new ArrayList<>();

    public JavaRootNode(String javaFileName) {
        this.javaFileName = javaFileName;
    }
}
