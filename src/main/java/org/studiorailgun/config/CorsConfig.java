package org.studiorailgun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.studiorailgun.SelfSurveyApplication;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println(":D 3");
        registry.addMapping("/**")
        // .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedOrigins("http://localhost:8000","http://localhost:8080", SelfSurveyApplication.domain)
        .allowCredentials(true)
        // .allowedHeaders("*")
        ;
    }
}