package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reputation.ReputationChange;
import com.validate.monorepo.commonlibrary.model.reputation.TopUser;

import java.util.List;
import java.util.Optional;

public interface CustomReputationRepository {
	
	List<TopUser> findTop5UsersLast24Hours();
	Optional<ReputationChange> findByUserIdAndPostOrReplyId(String userId, String postId, String replyId);
	
	
}
