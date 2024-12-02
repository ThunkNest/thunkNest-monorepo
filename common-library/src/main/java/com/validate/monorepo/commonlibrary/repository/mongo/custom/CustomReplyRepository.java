package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomReplyRepository {
	
	Reply editReply(final String replyId, final String newText, final List<User> newTaggedUsers);
	void updateVoteCount(String postId, long upVoteCount, long downVoteCount);
	Page<Reply> findRepliesByTaggedUserId(String userId, Pageable pageable);
	Page<Reply> findAllRepliesAndIsDeletedFalse(Pageable pageable);
	Page<Reply> findAllById(List<String> replyIds, Pageable pageable);
	Page<Reply> findAllRepliesByAuthor(String userId, Pageable pageable);
	void updateUserInAuthoredPosts(User user);
}
