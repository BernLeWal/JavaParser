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
            "sealed",

            "class",
            "interface",
            "record",

            "implements",
            "extends",

            "this",
            "super",

            // Members
            "default",
            "static",
            "final",
            "volatile",
            "throws",
            "synchronized",

            "if",
            "else",
            "switch",
            "case",
            "break",
            "while",
            "for",
            "do",
            "try",
            "catch",

            "new",
            "throw",

            "instanceof",

    };

    public static final String[] PRIMITIVES = {
            "void",

            "boolean",
            "char",
            "byte",
            "short",
            "int",
            "long",
            "float",
            "double",

            "true",
            "false",

            "null",
    };

    public static final String[] ASSIGNMENT_OPERATORS = {
            "=",
            "*=",
            "/=",
            "%=",
            "+=",
            "-=",
            "<<=",
            ">>=",
            ">>>=",
            "&=",
            "^=",
            "|=",
    };

    public static final String[] RELATIONAL_OPERATORS = {
            "<",
            ">",
            "<=",
            ">=",
    };

    public static final String[] SHIFT_OPERATORS = {
            "<<",
            ">>",
            ">>>",
    };
}
