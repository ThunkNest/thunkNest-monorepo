package com.validate.monorepo.voteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo"})
public class VoteServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VoteServiceApplication.class, args);
	}
}