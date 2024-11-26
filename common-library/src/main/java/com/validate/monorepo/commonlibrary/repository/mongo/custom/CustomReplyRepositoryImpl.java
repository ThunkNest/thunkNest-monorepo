package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class CustomReplyRepositoryImpl implements CustomReplyRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomReplyRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Reply updateReply(String replyId, String newText, List<User> newTaggedUsers) {
		// Find the reply by ID
		Query query = new Query(Criteria.where("_id").is(replyId));
		
		// Define the fields to update
		Update update = new Update()
				.set("text", newText)
				.set("taggedUsers", newTaggedUsers)
				.set("isEdited", true);
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				Reply.class
		);
	}
	
	@Override
	public List<Reply> findRepliesByTaggedUserId(String userId) {
		return List.of();
	}
	
	@Override
	public void upVoteReply(String replyId, String userId) {
	
	}
	
	@Override
	public void removeUpVoteReply(String replyId, String userId) {
	
	}
	
	@Override
	public void downVoteReply(String replyId, String userId) {
	
	}
	
	@Override
	public void removeDownVoteReply(String replyId, String userId) {
	
	}
}
