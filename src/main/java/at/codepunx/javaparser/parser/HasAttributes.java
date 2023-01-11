package at.codepunx.javaparser.parser;

public interface HasAttributes<K, V extends NodeInterface> {

    void setAttribute(K key, V node);

    void removeAttribute(K key);

    V getAttribute(K key);

    boolean hasAttribute(K key);
}
