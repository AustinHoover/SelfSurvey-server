package org.studiorailgun;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SelfSurveyApplication extends SpringBootServletInitializer {

	public static String username = "";
	public static String password = "";

	public static void main(String[] args) throws Throwable {
		//parse cli options

		// create Options object
		Options options = new Options();
		// add t option
		options.addOption("u", true, "username");
		options.addOption("p", true, "password");
		//actual parser
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if(cmd.hasOption("u") && cmd.hasOption("p")){
			username = cmd.getOptionValue("u");
			password = cmd.getOptionValue("p");
		} else {
			System.err.println("Error, need to include username and password flags");
			System.exit(1);
		}


		//start real app
		SpringApplication.run(SelfSurveyApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SelfSurveyApplication.class);
	}

}
