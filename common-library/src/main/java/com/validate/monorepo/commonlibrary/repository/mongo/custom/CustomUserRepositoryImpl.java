package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
}