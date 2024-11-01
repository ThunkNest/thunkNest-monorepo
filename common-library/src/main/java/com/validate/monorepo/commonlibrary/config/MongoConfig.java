package com.validate.monorepo.commonlibrary.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableMongoRepositories(basePackages = "com.validate.monorepo.commonlibrary.repository.mongo")
public class MongoConfig {
	
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoUri);
	}
	
	@Bean
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		return new MongoTemplate(mongoClient, databaseName);
	}
	
	@Bean
	public @NonNull MongoDatabase mongoDatabase(@NonNull MongoClient mongoClient) {
		return mongoClient.getDatabase(databaseName);
	}
}
