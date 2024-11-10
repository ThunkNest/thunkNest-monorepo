package com.validate.monorepo.commonlibrary.model.neo.post;

import java.util.UUID;

public record CreatePostRequest(
		String title,
		String description,
		UUID authorId
) {}
