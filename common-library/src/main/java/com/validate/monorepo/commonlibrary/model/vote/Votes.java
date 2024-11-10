package com.validate.monorepo.commonlibrary.model.vote;

import org.springframework.data.annotation.Id;

import java.util.Set;

public record Votes(
	@Id String postId,
	Set<String> upVoters,
	Set<String> downVoters
) {}
