package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;

import java.util.Optional;

public interface CustomPostRepository {
	
	Optional<Post> addReplyToComment(String postId, String commentId, Comment reply);

}
