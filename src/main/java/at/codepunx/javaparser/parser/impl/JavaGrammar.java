package at.codepunx.javaparser.parser.impl;

import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;

public class JavaGrammar {
    private final String javaFileName;

    public JavaGrammar(String javaFileName) {
        this.javaFileName = javaFileName;
    }

    public JavaRootNode createRootNode(TokenReader<JavaTokenType> reader) throws TokenReaderException {
        JavaRootNode root = new JavaRootNode(javaFileName);

        boolean found;
        do {
            found = false;
            if ( found |= reader.tryReadToken(JavaTokenType.COMMENT_LINE) )
                root.getCommentsList().add( reader.readToken(JavaTokenType.COMMENT_LINE).getValue() );
            else if ( found |= reader.tryReadToken(JavaTokenType.COMMENT_BLOCK) )
                root.getCommentsList().add( reader.readToken(JavaTokenType.COMMENT_BLOCK).getValue() );
        }
        while( found );

        if ( reader.tryReadToken(JavaTokenType.KEYWORD, "package") )
            root.setJavaPackage( createPackageNode(reader) );

        do {
            found = false;
            if ( found |= reader.tryReadToken(JavaTokenType.COMMENT_LINE) )
                root.getCommentsList().add( reader.readToken(JavaTokenType.COMMENT_LINE).getValue() );
            else if ( found |= reader.tryReadToken(JavaTokenType.COMMENT_BLOCK) )
                root.getCommentsList().add( reader.readToken(JavaTokenType.COMMENT_BLOCK).getValue() );
            else if ( found |= reader.tryReadToken(JavaTokenType.KEYWORD, "import") )
                root.getImportList().add( createImportNode(reader) );
        }
        while( found );

        return root;
    }

    private JavaPackageNode createPackageNode(TokenReader<JavaTokenType> reader) throws TokenReaderException {
        reader.readToken( JavaTokenType.KEYWORD, "package");
        JavaPackageNode packageNode = new JavaPackageNode();

        StringBuilder packageName = new StringBuilder();
        packageName.append(reader.readToken(JavaTokenType.NAME).getValue());
        while( reader.tryReadToken(JavaTokenType.DOT) ) {
            packageName.append(reader.readToken(JavaTokenType.DOT).getValue());
            packageName.append(reader.readToken(JavaTokenType.NAME).getValue());
        }
        packageNode.setPackageName(packageName.toString());

        reader.readToken(JavaTokenType.SEMIKOLON);
        return packageNode;
    }

    private JavaImportNode createImportNode(TokenReader<JavaTokenType> reader) throws TokenReaderException {
        reader.readToken( JavaTokenType.KEYWORD, "import");
        JavaImportNode javaImportNode = new JavaImportNode();

        StringBuilder fqcn = new StringBuilder();
        fqcn.append(reader.readToken(JavaTokenType.NAME).getValue());
        while( reader.tryReadToken(JavaTokenType.DOT) ) {
            fqcn.append(reader.readToken(JavaTokenType.DOT).getValue());
            fqcn.append(reader.readToken(JavaTokenType.NAME).getValue());
        }
        javaImportNode.setFqcn(fqcn.toString());

        reader.readToken(JavaTokenType.SEMIKOLON);
        return javaImportNode;
    }


}
