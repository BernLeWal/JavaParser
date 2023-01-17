package at.codepunx.javaparser.parser;

import at.codepunx.javaparser.parser.grammar.Node;
import at.codepunx.javaparser.parser.grammar.NodeProvider;
import at.codepunx.javaparser.parser.grammar.ValueProvider;
import at.codepunx.javaparser.tokenizer.TokenReader;
import at.codepunx.javaparser.tokenizer.TokenReaderException;
import at.codepunx.javaparser.tokenizer.TokenTypeInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Parser<T extends TokenTypeInterface> {

    public abstract TokenReader<T> getReader();

    @Getter
    @Setter
    private Node currentNode;

    public NodeProvider mandatory(Function<Parser<T>, Node> func) throws ParseException {
        try {
            return new NodeProvider(func.apply(this));
        }
        finally {
            setCurrentNodeToParent();
        }
    }

    public NodeProvider mandatoryOneOf(Function<Parser<T>, Node>... funcs) throws ParseException {
        var backupNode = currentNode;
        for( var func : funcs ) {
            var nodeProvider = optional(func);
            currentNode = backupNode;
            if ( nodeProvider != null && !nodeProvider.isEmpty()) {
                return nodeProvider;
            }
        }
        throw new ParseException( "None of mandatory created, looked for " + funcs );
    }

    public NodeProvider optional(Function<Parser<T>, Node> func) {
        var backupReader = (TokenReader<T>)getReader().clone();
        try {
            return new NodeProvider( func.apply(this) );
        }
        catch ( ParseException e ) {
            getReader().revertTo(backupReader);
            return new NodeProvider();
        }
        finally {
            setCurrentNodeToParent();
        }
    }

    public NodeProvider optionalOneOf(Function<Parser<T>, Node>... funcs) throws ParseException {
        for( var func : funcs ) {
            var nodeProvider = optional(func);
            if ( nodeProvider!= null && !nodeProvider.isEmpty() )
                return nodeProvider;
        }
        return new NodeProvider();
    }


    public ValueProvider mandatoryToken(T tokenType) throws ParseException {
        try {
            return new ValueProvider( getReader().readToken(tokenType).getValue() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public ValueProvider mandatoryToken(T tokenType, String value) throws ParseException {
        try {
            return new ValueProvider( getReader().readToken(tokenType, value).getValue() );
        } catch (TokenReaderException e) {
            throw new ParseException(e);
        }
    }

    public ValueProvider optionalToken(T tokenType) {
        if ( getReader().tryReadToken(tokenType) ) {
            try {
                return new ValueProvider(getReader().readToken(tokenType).getValue());
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return new ValueProvider();
    }

    public boolean optionalToken(T tokenType, String value) {
        if ( getReader().tryReadToken(tokenType, value) ) {
            try {
                getReader().readToken(tokenType, value);
                return true;
            } catch (TokenReaderException e) {
                throw new ParseException(e);
            }
        }
        return false;
    }


    // TODO calculate created nodes count internally, take away dependency to parent.getCount()
    public int multiple(Consumer<TokenReader<T>> func) throws ParseException {
        int startChildCount = currentNode.getCount();
        int childCount;
        do {
            childCount = currentNode.getCount();
            var backupReader = (TokenReader<T>)getReader().clone();
            try {
                func.accept(getReader());
            }
            catch ( ParseException e ) {
                getReader().revertTo(backupReader);
                break;
            }
            finally {
                setCurrentNodeToParent();
            }
        } while ( childCount < getCurrentNode().getCount() );
        return currentNode.getCount() - startChildCount;
    }

    @Deprecated
    // ATTENTION: if failed, optionals in func may already applied the sendTo() consumer! Would need to revert this!
    public void amount(int minCount, int maxCount, Consumer<TokenReader<T>> func) throws ParseException {
        int childCount = currentNode.getCount();
        var backupReader = (TokenReader<T>)getReader().clone();
        try {
            func.accept(getReader());
        }
        catch ( ParseException e ) {
            getReader().revertTo(backupReader);
            throw e;
        }
        finally {
            setCurrentNodeToParent();
        }
        if ( currentNode.getCount() < (childCount+minCount) )
            throw new ParseException( String.format("In %s only %d children created, expected minimum amount %d!", currentNode, currentNode.getCount()-childCount, minCount) ) ;
        if ( currentNode.getCount() > (childCount+maxCount) )
            throw new ParseException( String.format("In %s the amount of %d children created, expected maximum %d!", currentNode, currentNode.getCount()-childCount, maxCount) ) ;
    }

    @Deprecated
    // ATTENTION: if failed, optionals in func may already applied the sendTo() consumer! Would need to revert this!
    public void oneOf(Consumer<TokenReader<T>> func) throws ParseException {
        amount(1, 1, func);
    }


    private void setCurrentNodeToParent() {
        if ( currentNode!=null && currentNode.getParent()!=null )
            currentNode = currentNode.getParent();
    }
}
