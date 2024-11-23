package com.validate.monorepo.commonlibrary.model.vote.neo4j;

import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
public record Upvote(
		@Id
		@GeneratedValue
		Long id,
		
		@TargetNode
		User user,
		
		@CreatedDate
		LocalDateTime createdAt
) {}
