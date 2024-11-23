package com.validate.monorepo.commonlibrary.model.reputation.neo4j;

import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.UUID;

@Node
public record ReputationChange (
	@Id
	@GeneratedValue(GeneratedValue.UUIDGenerator.class)
	UUID id,
	
	@Relationship(type = "AFFECTS", direction = Relationship.Direction.OUTGOING)
	User user,
	
	int pointsChanged,
	LocalDateTime timestamp
){}
