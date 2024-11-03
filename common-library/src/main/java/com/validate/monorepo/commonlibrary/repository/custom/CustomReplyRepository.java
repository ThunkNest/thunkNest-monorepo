package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Reply;

import java.util.List;

public interface CustomReplyRepository {
	
	List<Reply> findTopLevelRepliesByPostId(String postId);
	List<Reply> findRepliesByReplyId(String replyId);

}
