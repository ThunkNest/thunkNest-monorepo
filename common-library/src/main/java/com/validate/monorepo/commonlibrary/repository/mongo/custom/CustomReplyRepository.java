package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;

import java.util.List;

public interface CustomReplyRepository {
	
	Reply updateReply(final String replyId, final String newText, final List<User> newTaggedUsers);
	List<Reply> findRepliesByTaggedUserId(String userId);
	void upVoteReply(final String replyId, final String userId);
	void removeUpVoteReply(final String replyId, final String userId);
	void downVoteReply(final String replyId, final String userId);
	void removeDownVoteReply(final String replyId, final String userId);
	
}
