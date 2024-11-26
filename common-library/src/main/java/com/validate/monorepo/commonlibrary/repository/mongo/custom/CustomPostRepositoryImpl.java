package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomPostRepositoryImpl implements CustomPostRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomPostRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void addReplyToPost(String postId, String replyId) {
		Query query = new Query(Criteria.where("_id").is(postId));
		Update update = new Update().push("replies", replyId);
		mongoTemplate.updateFirst(query, update, Post.class);
	}
	
	@Override
	public void removeReplyFromPost(String postId, String replyId) {
		Query query = new Query(Criteria.where("_id").is(postId));
		Update update = new Update().pull("replies", Query.query(Criteria.where("_id").is(replyId)));
		// Perform the update operation
		mongoTemplate.updateFirst(query, update, Post.class);
	}
	
	@Override
	public List<Post> findAllPostsByAuthor(String userId) {
		Query query = new Query(Criteria.where("author._id").is(userId));
		return mongoTemplate.find(query, Post.class);
	}
	
	@Override
	public List<Post> findAllPostsUserInteractedWith(String userId) {
		Query query = new Query(new Criteria().orOperator(
				Criteria.where("upvotedBy._id").is(userId),
				Criteria.where("downvotedBy._id").is(userId),
				Criteria.where("replies.author._id").is(userId)
		));
		return mongoTemplate.find(query, Post.class);
	}

}
