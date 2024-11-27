package com.validate.monorepo.commonlibrary.model.post;

public record PostRequest(
		String title,
		String description,
		String authorUserId,
		boolean openToCoFounder
) {}
