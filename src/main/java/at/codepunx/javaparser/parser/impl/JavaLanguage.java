package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.parser.grammar.KeywordTypeInterface;

public class JavaLanguage {
    public enum Visibility implements KeywordTypeInterface {
        PUBLIC("public"),
        PROTECTED("protected"),
        PRIVATE("private"),
        DEFAULT("");

        final String value;

        Visibility(String value) {
            this.value = value;
        }

        public String value() { return value; }
    }

    public enum Abstract implements KeywordTypeInterface {
        ABSTRACT( "abstract" );

        final String value;

        Abstract(String value) {
            this.value = value;
        }

        public String value() { return value; }
    }

    public enum Static implements KeywordTypeInterface {
        STATIC( "static" );

        final String value;

        Static(String value) { this.value = value; }

        public String value() { return value; }
    }

    public enum Final implements KeywordTypeInterface {
        FINAL( "final" );

        final String value;

        Final(String value) { this.value = value; }

        public String value() { return value; }
    }

    public enum Synchronized implements KeywordTypeInterface {
        SYNCHRONIZED( "synchronized" );

        final String value;

        Synchronized(String value) { this.value = value; }

        public String value() { return value; }
    }

}
