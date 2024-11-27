package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;

import java.util.List;

public interface CustomReplyRepository {
	
	Reply editReply(final String replyId, final String newText, final List<User> newTaggedUsers);
	List<Reply> findRepliesByTaggedUserId(String userId);
	List<Reply> findAllRepliesAndIsDeletedFalse();
	
}
