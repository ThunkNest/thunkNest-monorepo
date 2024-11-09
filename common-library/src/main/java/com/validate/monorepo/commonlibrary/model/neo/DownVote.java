package com.validate.monorepo.commonlibrary.model.neo;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public record DownVote(
		@Id
		@GeneratedValue
		Long id,
		
		@TargetNode
		User user
) {}
