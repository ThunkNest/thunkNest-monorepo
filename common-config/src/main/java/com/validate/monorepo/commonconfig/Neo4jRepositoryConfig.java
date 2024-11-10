package com.validate.monorepo.commonconfig;

import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.validate.monorepo.commonlibrary.repository.neo4j")
public class Neo4jRepositoryConfig {
	// This will ensure that repositories in the common-library are scanned
}
