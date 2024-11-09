package com.validate.monorepo.commonlibrary.model.neo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;

public record Post(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		Long id,
		String title,
		String description,
		boolean isDeleted,
		
		@CreatedDate
		LocalDateTime createdAt,
		
		@Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
		User author,

		@Relationship(type = "REPLIED_TO", direction = Relationship.Direction.OUTGOING)
		List<Reply> replies
) { }
