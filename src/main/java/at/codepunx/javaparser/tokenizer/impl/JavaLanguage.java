package at.codepunx.javaparser.tokenizer.impl;

public class JavaLanguage {
    public static final String[] KEYWORDS = {
            // Java File & File-Header
            "package",
            "import",

            // Declaration
            "public",
            "protected",
            "private",

            "class",
            "interface",
            "record",

            // Members
            "default",
            "static",
            "final",
            "volatile",
            "throws",

            "if",
            "else",
            "switch",
            "case",
            "break",
            "while",
            "for",
            "do",

            "new",

    };

    public static final String[] PRIMITIVES = {
            "void",

            "boolean",
            "char",
            "byte",
            "integer",
            "long",
            "float",
            "double",

            "true",
            "false",

            "null",
    };
}