package com.validate.monorepo.reputationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo"})
@EnableCaching
@EnableScheduling
public class ReputationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReputationServiceApplication.class, args);
	}
}
