package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Post addReplyToComment(String postId, String parentCommentId, Comment reply) {
		// Step 1: Retrieve the Post by postId
		Query query = new Query(Criteria.where("id").is(postId));
		Post post = mongoTemplate.findOne(query, Post.class);
		
		if (post == null) {
			log.error("Post not found with id: " + postId);
			return null;
		}
		
		// Step 2: Find the specific comment with parentCommentId within the post
		Optional<Comment> parentCommentOpt = post.comments().stream()
				.filter(comment -> comment.id().equals(parentCommentId))
				.findFirst();
		
		if (parentCommentOpt.isEmpty()) {
			log.error("Comment not found with id: " + parentCommentId + " in post: " + postId);
			return null;
		}
		
		Comment parentComment = parentCommentOpt.get();
		log.info("Found parent comment: " + parentComment);
		
		// Step 3: Add reply to the comment's replies list
		parentComment.replies().add(reply);
		log.info("Reply added to parent comment's replies");
		
		// Step 4: Save the updated post back to MongoDB
		Post updatedPost = mongoTemplate.save(post);
		log.info("Updated post saved successfully with new reply");
		
		return updatedPost;
	}
	
//	@Override
//	public Post addReplyToComment(String postId, String parentCommentId, Comment reply) {
//		Query query = new Query(Criteria.where("id").is(postId)
//				.and("comments.id").is(parentCommentId));
//
//		// Use Update object to push reply into the specific comment's replies array
//		Update update = new Update().push("comments.$[elem].replies", reply);
//
//		// Correct filter array criteria
//		update.filterArray(Criteria.where("elem.id").is(parentCommentId));
//
//		log.info("Executing update for reply addition");
//
//		// Use findAndModify with FindAndModifyOptions
//		return mongoTemplate.findAndModify(query, update,
//				FindAndModifyOptions.options().returnNew(true), Post.class);
//	}
}
