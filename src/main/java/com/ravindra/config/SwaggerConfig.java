package com.ravindra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is Swagger API custom configuration class which provides the ResFul
 * WebService API documentation
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * This method is used generate the API documentation using Swagger 2 API
	 * 
	 * @return Docket
	 */
	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ravindra.controller"))
				.paths(PathSelectors.ant("/openmapsrestapi/*")).build().apiInfo(metaData());
	}

	/**
	 * This method generates the metadata for the Swagger Dashboard
	 * 
	 * @return ApiInfo
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Holon Consulting Assessment")
				.description("\"Holon Consulting Interview Assessment\"").version("1.0.0")
				.contact(new Contact("Rsavindra Kakarla", "www.trendingtechnologies.net", "kvsravindrareddy@gmail.com"))
				.build();
	}
}