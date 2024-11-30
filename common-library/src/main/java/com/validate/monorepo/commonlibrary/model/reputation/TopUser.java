package com.validate.monorepo.commonlibrary.model.reputation;

import com.validate.monorepo.commonlibrary.model.user.User;

public record TopUser (
		String id,
		int totalPoints,
		User user
) {}
