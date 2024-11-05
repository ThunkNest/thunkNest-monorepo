package com.validate.monorepo.commonlibrary.model.vote;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "votes")
public record Votes(
	@Id String postId,
	Set<String> upVoters,
	Set<String> downVoters
) {}
