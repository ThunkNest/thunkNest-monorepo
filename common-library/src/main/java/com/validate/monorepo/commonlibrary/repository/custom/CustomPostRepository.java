package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;

public interface CustomPostRepository {
	
	Post addReplyToComment(String postId, String commentId, Comment reply);

}
