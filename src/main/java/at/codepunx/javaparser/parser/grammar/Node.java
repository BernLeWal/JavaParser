package at.codepunx.javaparser.parser.grammar;

import at.codepunx.javaparser.parser.Composable;
import at.codepunx.javaparser.parser.HasValue;
import at.codepunx.javaparser.parser.NodeInterface;
import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.grammar.comments.CommentInterface;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@EqualsAndHashCode
public abstract class Node implements NodeInterface, Composable<Node>, HasValue<String> {
    @Getter
    @Setter
    private Node parent;

    @Getter
    private final List<Node> children = new ArrayList<>();
    @Getter
    private final List<Node> comments = new ArrayList<>();

    @Getter
    @Setter
    private String value;


    @Override
    public void addChild(Node child) {
        if ( child instanceof CommentInterface )
            comments.add(child);
        else
            children.add(child);
        child.setParent(this);
    }

    public int getChildCount() {
        return comments.size() + children.size();
    }

    @Override
    public boolean removeChild(Node child) {
        boolean removed;
        if ( child instanceof CommentInterface )
            removed = comments.remove(child);
        else
            removed = children.remove(child);

        if ( removed ) {
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


    protected void mandatory(TokenReader<JavaTokenType> reader, Function<TokenReader<JavaTokenType>, Node> func) throws ParseException {
        addChild( func.apply(reader) );
    }

    protected String mandatoryToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType) throws ParseException {
        try {
            return reader.readToken(tokenType).getValue();
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    protected void mandatoryToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType, String value) throws ParseException {
        try {
            reader.readToken(tokenType, value);
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }


    protected boolean optional(TokenReader<JavaTokenType> reader, Function<TokenReader<JavaTokenType>, Node> func) {
        TokenReader<JavaTokenType> backupReader = (TokenReader<JavaTokenType>)reader.clone();
        try {
            addChild( func.apply(reader) );
            return true;
        }
        catch ( ParseException e ) {
            reader.revertTo(backupReader);
            return false;
        }
    }

    protected String optionalToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType) {
        if ( reader.tryReadToken(tokenType) ) {
            try {
                return reader.readToken(tokenType).getValue();
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return null;
    }

    protected boolean optionalToken(TokenReader<JavaTokenType> reader, JavaTokenType tokenType, String value) {
        if ( reader.tryReadToken(tokenType, value) ) {
            try {
                reader.readToken(tokenType, value);
                return true;
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return false;
    }


    protected int multiple(TokenReader<JavaTokenType> reader, Consumer<TokenReader<JavaTokenType>> func) throws ParseException {
        int startChildCount = getChildCount();
        int childCount;
        do {
            childCount = children.size() + comments.size();
            TokenReader<JavaTokenType> backupReader = (TokenReader<JavaTokenType>)reader.clone();
            try {
                func.accept(reader);
            }
            catch ( ParseException e ) {
                reader.revertTo(backupReader);
                break;
            }
        } while ( childCount < getChildCount() );
        return getChildCount() - startChildCount;
    }


    @Override
    public String toString() {
        return String.format("%s(%s)", this.getClass().getSimpleName(), getValue());
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
