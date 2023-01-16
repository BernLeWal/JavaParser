package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.Composable;
import at.codepunx.javaparser.parser.HasAttributes;
import at.codepunx.javaparser.parser.HasValue;
import at.codepunx.javaparser.parser.NodeInterface;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@EqualsAndHashCode
public abstract class Node implements NodeInterface, Composable<Node>, HasValue<String>, HasAttributes<String, NodeInterface> {
    @Getter
    @Setter
    private Node parent;
    @Getter
    private int tokenNr;

    @Getter
    private final List<Node> children = new ArrayList<>();
    @Getter
    private final List<Node> comments = new ArrayList<>();

    @Getter
    @Setter
    private String value = null;

    private final Map<String, NodeInterface> attributes = new HashMap<>();




    public void addComment(Node child) {
        comments.add(child);
        child.setParent(this);
    }

    public boolean removeComment(Node child) {
        if ( comments.remove(child) ) {
            child.setParent(null);
            return true;
        } else
            return false;
    }


    // implementation Composable<Node>
    @Override
    public void addChild(Node child) {
        if ( child!=null ) {
            children.add(child);
            child.setParent(this);
        }
    }

    public int getCount() {
        return comments.size() + children.size() + attributes.size();
    }

    @Override
    public boolean removeChild(Node child) {
        if ( children.remove(child) ) {
            child.setParent(null);
            return true;
        } else
            return false;
    }

    public <T> Optional<T> getChild(final Class<T> nodeClass) {
        return (Optional<T>)children.stream().filter( n->n.getClass().equals(nodeClass) ).findFirst();
    }

    public <T> List<T> getChildren(final Class<T> nodeClass) {
        return children.stream().filter( n->n.getClass().equals(nodeClass) ).map( n->(T)n ).toList();
    }

    // implementation HasAttributes<Node>
    @Override
    public void setAttribute(String key, NodeInterface value) {
        if ( value != null )
            attributes.put( key, value );
        else if ( hasAttribute(key) )
            removeAttribute( key );
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    @Override
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    @Override
    public NodeInterface getAttribute(String key) {
        return attributes.get(key);
    }

    public <T> T getAttributeValue(Class<T> valueClass) {
        var attr = getAttribute(valueClass.getSimpleName());
        if ( attr!=null && attr instanceof HasValue<?> )
            return ((HasValue<T>)attr).getValue();
        return null;
    }


    @Override
    public String toString() {
        return String.format("%s(%s)", this.getClass().getSimpleName(), getValue());
    }

    public String toStringRecursive(boolean showType, int indentCount) {
        String indent = "\t".repeat(indentCount);
        StringBuilder sb = new StringBuilder();
        if( showType )
            sb.append( indent + toString() );
        if( !isLeaf() ) {
            sb.append( " {\n");
            boolean isFirst = true;
            for (Node child : children) {
                if (isFirst)
                    isFirst = false;
                else
                    sb.append(", \n");
                sb.append(child.toStringRecursive(true, indentCount+1));
            }
            sb.append("\n" + indent + "} ");
        }
        return sb.toString();
    }
}
