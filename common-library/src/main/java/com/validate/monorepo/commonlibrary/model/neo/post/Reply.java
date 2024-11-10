package com.validate.monorepo.commonlibrary.model.neo.post;

import com.validate.monorepo.commonlibrary.model.neo.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node
public record Reply(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		UUID id,
		String text,
		
		@Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
		User author,

		@Relationship(type = "REPLIED_TO", direction = Relationship.Direction.OUTGOING)
		List<Reply> replies,
		
		@CreatedDate
		LocalDateTime createdAt
) { }
