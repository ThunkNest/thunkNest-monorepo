package com.validate.monorepo.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration.class )
@ComponentScan(basePackages = {"com.validate.monorepo"})
public class SearchServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}
}