package at.codepunx.javaparser.parser.grammar;

import java.util.function.Consumer;

public class ValueProvider implements ProviderInterface {
    private final String value;

    public ValueProvider() { value = null; }
    public ValueProvider(String value) { this.value = value; }

    public void sendTo(Consumer<String> func) {
        if ( value!=null )
            func.accept(value);
    }

    @Override
    public boolean isEmpty() { return value==null; }
    public String getValue() { return value; }
}
