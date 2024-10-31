package com.validate.monorepo.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo"})
public class PostServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}
}