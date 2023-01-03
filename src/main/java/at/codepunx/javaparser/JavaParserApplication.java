package at.codepunx.javaparser;

import at.codepunx.javaparser.app.CmdLineParser;
import at.codepunx.javaparser.services.JavaParserAppServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaParserApplication {

	@Autowired
	private JavaParserAppServiceInterface app;

	public static void main(String[] args) {
		SpringApplication.run(JavaParserApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args->{
			var params = new CmdLineParser().parse(args);
			app.run(params);
		});
	}
}
