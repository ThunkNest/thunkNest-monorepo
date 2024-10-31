package com.linkup.monorepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo"})
public class MonorepoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonorepoApplication.class, args);
	}
}
