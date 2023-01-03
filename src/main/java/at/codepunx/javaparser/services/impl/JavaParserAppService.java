package at.codepunx.javaparser.services.impl;

import at.codepunx.javaparser.app.AppParams;
import at.codepunx.javaparser.services.JavaParserAppServiceInterface;
import at.codepunx.javaparser.tokenizer.impl.JavaTokenizer;
import at.codepunx.javaparser.tokenizer.TokenizerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

        JavaTokenizer tokenizer = new JavaTokenizer();
        try {
            var tokens = tokenizer.tokenize( params.getSourceRootDirectory().toUri());
            log.info( "Tokenizer returned:\n" + tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\n")) );
        } catch (TokenizerException e) {
            log.error( e.getMessage() );
            e.printStackTrace();
        }
    }
}
