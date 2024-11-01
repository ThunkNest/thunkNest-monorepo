package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class CustomPostRepositoryImpl implements CustomPostRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Optional<Post> addReplyToComment(String postId, String commentId, Comment reply) {
		Query query = Query.query(Criteria.where("_id").is(postId));
		
		Update update = new Update().push("comments.$[comment].replies", reply);
		
		update.filterArray("comment._id", commentId);
		
		FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
		
		Post updatedPost = mongoTemplate.findAndModify(
				query,
				update,
				options,
				Post.class
		);
		
		return Optional.ofNullable(updatedPost);
	}
}
