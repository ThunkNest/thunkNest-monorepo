package com.validate.monorepo.commonlibrary.model.neo;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public record Reply(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		Long id,
		String text,
		
		@Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
		User author,

		@Relationship(type = "REPLIED_TO", direction = Relationship.Direction.OUTGOING)
		List<Reply> replies
) { }
