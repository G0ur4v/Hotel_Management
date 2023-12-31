package com.service.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
		System.out.println(
				"---------------------------Authentication Service Started -------------------------------------------");

	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
