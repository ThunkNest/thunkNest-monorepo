package com.validate.monorepo.commonlibrary.model.neo.vote;

import com.validate.monorepo.commonlibrary.model.neo.user.User;
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
