package at.codepunx.javaparser.services.impl;

import at.codepunx.javaparser.app.AppParams;
import at.codepunx.javaparser.parser.ParseException;
import at.codepunx.javaparser.parser.impl.JavaParser;
import at.codepunx.javaparser.services.JavaParserAppServiceInterface;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenizer;
import at.codepunx.javaparser.tokenizer.TokenizerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JavaParserAppService implements JavaParserAppServiceInterface {

    @Override
    public void run(AppParams params) {
        if ( params.getStopWithExitCode().isPresent() )
            System.exit( params.getStopWithExitCode().get() );

        if ( params.getSourceRootDirectory()==null ) {
            log.warn("No root-directory containing the source-files is given.");
            System.exit(-2);
        }
        if ( !params.getSourceRootDirectory().toFile().exists() ) {
            log.warn("File " + params.getSourceRootDirectory() + " not found!");
            System.exit(-2);
        }

        try {
            JavaTokenizer tokenizer = new JavaTokenizer();
            var tokens = tokenizer.tokenize( params.getSourceRootDirectory().toUri());
            log.info( String.format("Tokenizer returned %d tokens", tokens.size()) );
            //log.info( tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\n")) );

            JavaParser parser = new JavaParser();
            var rootNode = parser.parseJavaFile( params.getSourceRootDirectory().getFileName().toString(), tokens );
            log.info( "Parser returned: \n" + rootNode.toStringRecursive(true, 0));
            log.info( "Done.");

        } catch (TokenizerException e) {
            log.error( e.getMessage() );
            e.printStackTrace();
        } catch (ParseException e) {
            log.error( e.getMessage() );
            e.printStackTrace();
        }
    }
}
