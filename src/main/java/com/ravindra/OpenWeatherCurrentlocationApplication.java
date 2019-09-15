package com.ravindra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * This is main Spring Boot application class which bootstrap the application
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@SpringBootApplication
public class OpenWeatherCurrentlocationApplication extends SpringBootServletInitializer {
	
	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder build)
	{
		return build.sources(OpenWeatherCurrentlocationApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OpenWeatherCurrentlocationApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}