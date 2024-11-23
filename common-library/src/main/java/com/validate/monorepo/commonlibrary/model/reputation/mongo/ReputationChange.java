package com.validate.monorepo.commonlibrary.model.reputation.mongo;

import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reputationChange")
public record ReputationChange(
		@Id
		String id,
		User user,
		int pointsChanged,
		long timestamp
) {
}
