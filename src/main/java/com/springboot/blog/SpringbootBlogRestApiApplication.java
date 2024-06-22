package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Spring boot Blog app rest apis",
		description = "spring boot blog app description",
		version = "v1.0",
		contact = @Contact(
			name = "Danish",
			email = "dd007@gmail.com",
			url = ""
		),
		license = @License(
			name = "Apache 2.0",
			url = ""
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "Spring Boot blog app documentation",
		url = ""
	)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
