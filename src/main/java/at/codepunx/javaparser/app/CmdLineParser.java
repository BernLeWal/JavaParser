package at.codepunx.javaparser.app;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
public class CmdLineParser {
    @Getter
    private final AppParams params;

    public CmdLineParser() {
        this.params = new AppParams();
    }

    public AppParams parse(String[] args) {
        // interpret the cmd-line arguments
        int act = 0;
        while( act<args.length ) {
            if ((act = tryParseHelp(args, act)) == -1)
                return params;
            if ((act = tryParseInDirectory(args, act)) ==-1)
                return params;
            act++;
        }
        return params;
    }

    private int tryParseHelp(String[] args, int head) {
        if ( head >= args.length )
            return head;  // no more arguments
        if ( args[head].equals("--help") || args[head].equals("-h") || args[head].equals("-?")) {
            log.info("""
                    Java Parser
                    
                    Command Line application to parse Java sourcefiles.
                    
                    Usage: java jar JavaParserApplication.jar [OPTIONS]
                    
                    --help,-h,-?    Show this help text and exit
                    --in <filename>,-i <filename>   input java source-file

                    """);
            params.stopWithExitCode(-1);
            return -1;  // done
        }
        return head;

    }

    private int tryParseInDirectory(String[] args, int head) {
        if ( head >= args.length )
            return head;  // no more arguments
        if ( args[head].equals("--in") || args[head].equals("-i") ) {
            if (++head >= args.length ) {
                log.error("--in needs a filename as parameter!");
                return -1;  // not enought parameters
            }
            var filepath = Path.of(args[head]);
            if (!filepath.toFile().exists() ) {
                log.error( String.format("File %s not found!", args[head] ) );
                return -1;
            }
            params.setSourceRootDirectory( filepath );
            return ++head;
        }
        return head;
    }

}
