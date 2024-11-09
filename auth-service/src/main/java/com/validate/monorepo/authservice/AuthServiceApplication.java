package com.validate.monorepo.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo.commonlibrary", "com.validate.monorepo.postservice", "com.validate.monorepo.authservice"})
@EnableMongoRepositories(basePackages = {
		"com.validate.monorepo.commonlibrary.repository",
		"com.validate.monorepo.postservice.repository"
})
@EnableNeo4jRepositories(basePackages = "com.validate.monorepo.commonlibrary.repository.neo4j")
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}