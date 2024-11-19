package com.validate.monorepo.commonlibrary.model.event;

public record VoteEvent (
		ResourceType type,
		String resourceId,
		String voterId
) {}
