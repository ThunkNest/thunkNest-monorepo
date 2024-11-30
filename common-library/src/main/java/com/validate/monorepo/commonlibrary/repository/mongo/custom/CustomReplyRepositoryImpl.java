package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.Instant;
import java.util.List;

public class CustomReplyRepositoryImpl implements CustomReplyRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomReplyRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Reply editReply(String replyId, String newText, List<User> newTaggedUsers) {
		// Find the reply by ID
		Query query = new Query(Criteria.where("_id").is(replyId));
		
		// Define the fields to update
		Update update = new Update()
				.set("text", newText)
				.set("taggedUsers", newTaggedUsers)
				.set("isEdited", true)
				.set("editedAt", Instant.now().toEpochMilli());
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				Reply.class
		);
	}
	
	@Override
	public void updateVoteCount(String postId, long upVoteCount, long downVoteCount) {
		Query query = new Query(Criteria.where("_id").is(postId));
		Update update = new Update().set("upVoteCount", upVoteCount).set("downVoteCount", downVoteCount);
		mongoTemplate.updateFirst(query, update, Reply.class);
	}
	
	@Override
	public List<Reply> findRepliesByTaggedUserId(String userId) {
		Query query = new Query(Criteria.where("taggedUsers._id").is(userId).and("isDeleted").is(false));
		return mongoTemplate.find(query, Reply.class);
	}
	
	@Override
	public List<Reply> findAllRepliesAndIsDeletedFalse() {
		Query query = new Query(Criteria.where("isDeleted").is(false));
		return mongoTemplate.find(query, Reply.class);
	}
	
}
