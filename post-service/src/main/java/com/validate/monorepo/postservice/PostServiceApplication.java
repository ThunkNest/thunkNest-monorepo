package com.validate.monorepo.postservice;

import com.validate.monorepo.commonconfig.Neo4jConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"com.validate.monorepo"})
@Import(Neo4jConfig.class)
public class PostServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}
}
