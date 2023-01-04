package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.Node;
import lombok.Data;

@Data
public class JavaPackageNode extends Node {
    private String packageName;
}
