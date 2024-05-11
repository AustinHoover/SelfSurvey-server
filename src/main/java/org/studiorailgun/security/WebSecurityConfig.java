package org.studiorailgun.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.studiorailgun.SelfSurveyApplication;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("security");
		http
			.authorizeHttpRequests((requests) -> requests
				.antMatchers("/survey/**", "/surveyresponse/**").authenticated()
				.antMatchers("/login").permitAll()
				.anyRequest().permitAll()
			)
			.cors().and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().authenticationEntryPoint(authenticationEntryPoint());

		return http.build();
	}

	@Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        BasicAuthenticationEntryPoint entryPoint = 
          new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("adminrealm");
        return entryPoint;
    }

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username(SelfSurveyApplication.username)
				.password(SelfSurveyApplication.password)
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
