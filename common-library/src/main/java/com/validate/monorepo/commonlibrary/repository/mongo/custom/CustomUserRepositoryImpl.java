package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Optional<User> findByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		User user = mongoTemplate.findOne(query, User.class);
		return Optional.ofNullable(user);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		User user = mongoTemplate.findOne(query, User.class);
		return Optional.ofNullable(user);
	}
	
	@Override
	public List<User> searchByUsername(String usernameFragment) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").regex(".*" + usernameFragment + ".*", "i"));
		query.limit(10); // Limit to 10 results
		return mongoTemplate.find(query, User.class);
	}
	
	@Override
	public User upvoteReputationIncrease(String userId) {
		Query query = new Query(Criteria.where("_id").is(userId));
		Update update = new Update().inc("reputationScore", 3);
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				User.class
		);
	}
	
	@Override
	public User removeUpvoteReputationIncrease(String userId) {
		Query query = new Query(Criteria.where("_id").is(userId));
		Update update = new Update().inc("reputationScore", -3);
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				User.class
		);
	}
	
	@Override
	public User downVoteReputationDecrease(String userId) {
		Query query = new Query(Criteria.where("_id").is(userId));
		Update update = new Update().inc("reputationScore", -1);
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				User.class
		);
	}
	
	@Override
	public User removeDownVoteReputationDecrease(String userId) {
		Query query = new Query(Criteria.where("_id").is(userId));
		Update update = new Update().inc("reputationScore", 1);
		
		return mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true),
				User.class
		);
	}
	
}