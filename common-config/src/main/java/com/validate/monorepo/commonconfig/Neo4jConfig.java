package com.validate.monorepo.commonconfig;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;

@Slf4j
@Configuration
public class Neo4jConfig {
	
	@Bean
	public Driver driver() {
		Driver driver =  GraphDatabase.driver(
				"neo4j+s://101fc5ab.databases.neo4j.io",
				AuthTokens.basic("neo4j", "MdIfQnts-BwwPmm7gz44yyLEByaCv5dZ2tyrNxR6YV8")
		);
		return driver;
	}

	@Bean
	public Neo4jClient neo4jClient(Driver driver) {
		return Neo4jClient.create(driver);
	}

	@Bean
	public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext mappingContext) {
		return new Neo4jTemplate(neo4jClient, mappingContext);
	}
	
}
