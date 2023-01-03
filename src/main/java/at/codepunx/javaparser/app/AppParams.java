package at.codepunx.javaparser.app;

import lombok.Data;

import java.nio.file.Path;
import java.util.Optional;

@Data
public class AppParams {
    private Path sourceRootDirectory;
    private Optional<Integer> stopWithExitCode = Optional.empty();


    public void stopWithExitCode(int i) {
        stopWithExitCode = Optional.of(i);
    }
}
