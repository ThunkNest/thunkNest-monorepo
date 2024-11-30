package com.validate.monorepo.commonlibrary.model.reputation;

import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reputationChange")
public record ReputationChange(
		@Id
		String id,
		User user,
		String postId,
		String replyId,
		int pointsChanged,
		long timestamp
) {
}
