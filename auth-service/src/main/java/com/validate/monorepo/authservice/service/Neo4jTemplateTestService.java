package com.validate.monorepo.authservice.service;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

@Service
public class Neo4jTemplateTestService {
	private final Neo4jTemplate neo4jTemplate;
	
	public Neo4jTemplateTestService(Neo4jTemplate neo4jTemplate) {
		this.neo4jTemplate = neo4jTemplate;
	}
	
}
