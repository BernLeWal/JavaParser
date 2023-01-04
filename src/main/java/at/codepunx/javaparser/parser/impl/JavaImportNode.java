package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.Node;
import lombok.Data;

@Data
public class JavaImportNode extends Node {
    private String fqcn;    // full-qualified-class-name
}
