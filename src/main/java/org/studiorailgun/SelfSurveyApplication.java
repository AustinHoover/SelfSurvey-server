package org.studiorailgun;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.studiorailgun.config.Config;

import com.google.gson.Gson;

@SpringBootApplication
public class SelfSurveyApplication extends SpringBootServletInitializer {

	public static String username = "";
	public static String password = "";
	public static String domain = "";

	public static void main(String[] args) throws Throwable {

		//start real app
		SpringApplication.run(SelfSurveyApplication.class, args);
	}

	// @EventListener(ApplicationReadyEvent.class)
	// public void doSomethingAfterStartup() {
	// 	System.out.println("Run after startup..");
	// }

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		String catalinaBase = System.getProperty("catalina.home");
		try {
			String configRaw = Files.readString(new File(catalinaBase + "/config/selfsurveybe.json").toPath());
			Gson gson = new Gson();
			Config config = gson.fromJson(configRaw, Config.class);
			username = config.getUsername();
			password = config.getPassword();
			domain = config.getDomain();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return application.sources(SelfSurveyApplication.class);
    }


}
