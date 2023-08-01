package com.service.adminstration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AdminstrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminstrationApplication.class, args);
		System.out.println("Hello Adminstration");

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
