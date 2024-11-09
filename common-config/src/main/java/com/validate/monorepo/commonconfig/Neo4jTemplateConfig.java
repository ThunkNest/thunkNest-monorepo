package com.validate.monorepo.commonconfig;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;

@Configuration
public class Neo4jTemplateConfig {
	
	@Bean
	public Neo4jClient neo4jClient(Driver driver) {
		return Neo4jClient.create(driver);
	}
	
	@Bean
	public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext mappingContext) {
		return new Neo4jTemplate(neo4jClient, mappingContext);
	}
	
}
