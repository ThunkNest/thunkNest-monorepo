package com.validate.monorepo.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo.commonlibrary", "com.validate.monorepo.postservice"})
@EnableMongoRepositories(basePackages = {
	"com.validate.monorepo.commonlibrary.repository",
	"com.validate.monorepo.postservice.repository"
})
public class PostServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}
}
