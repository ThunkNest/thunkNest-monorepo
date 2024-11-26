package com.validate.monorepo.commonlibrary.model.post;

public record CreatePostRequest(
		String title,
		String description,
		String authorId
) {}
