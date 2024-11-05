package com.validate.monorepo.voteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.validate.monorepo.commonlibrary",
		"com.validate.monorepo.voteservice"
})
@EnableMongoRepositories(basePackages = {
		"com.validate.monorepo.commonlibrary.repository"
})

public class VoteServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VoteServiceApplication.class, args);
	}
}