package at.codepunx.javaparser.parser.grammar;

import java.util.function.Consumer;

public class NodeProvider {
    private final Node node;

    public NodeProvider() { node = null; }
    public NodeProvider(Node node) { this.node = node; }

    public void sendTo(Consumer<Node> func) {
        if ( node != null )
            func.accept(node);
    }
    public boolean hasNode() { return node!=null; }
    public Node getNode() { return node; }
}
