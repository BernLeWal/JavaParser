package at.codepunx.javaparser.parser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public abstract class Node implements NodeInterface, Composable<Node> {
    @Getter
    @Setter
    private Node parent;

    @Getter
    private final List<Node> children = new ArrayList<>();

    @Override
    public void addChild(Node child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public boolean removeChild(Node child) {
        if (children.remove(child)) {
            child.setParent(null);
            return true;
        } else
            return false;
    }


    public String toStringRecursive(boolean showType) {
        StringBuilder sb = new StringBuilder();
        if( showType )
            sb.append( toString() );
        if( !isLeaf() ) {
            sb.append("[");
            boolean isFirst = true;
            for (Node child : children) {
                if (isFirst)
                    isFirst = false;
                else
                    sb.append(", ");
                sb.append(child.toStringRecursive(true));
            }
            sb.append("] ");
        }
        return sb.toString();
    }
}
