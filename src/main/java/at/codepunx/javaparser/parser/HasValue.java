package at.codepunx.javaparser.parser;

public interface HasValue<T> {
    T getValue();
    void setValue(T value);
}
