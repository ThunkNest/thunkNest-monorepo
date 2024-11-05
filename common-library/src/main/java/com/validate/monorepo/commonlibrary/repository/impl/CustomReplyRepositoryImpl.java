package com.validate.monorepo.commonlibrary.repository.impl;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.repository.custom.CustomReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomReplyRepositoryImpl implements CustomReplyRepository {
	
	private final MongoTemplate mongoTemplate;
	
	public CustomReplyRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Reply> findTopLevelRepliesByPostId(String postId) {
		log.info("Finding top-level replies for post ID: {}", postId);
		Query query = new Query(Criteria.where("postId").is(postId)
				.and("parentReplyId").is(null));
		List<Reply> replies = mongoTemplate.find(query, Reply.class);
		log.debug("Found {} top-level replies for post ID: {}", replies.size(), postId);
		return replies;
	}
	
	@Override
	public List<Reply> findRepliesByParentReplyId(String parentReplyId) {
		log.info("Finding replies for reply ID: {}", parentReplyId);
		Query query = new Query(Criteria.where("parentReplyId").is(parentReplyId));
		List<Reply> replies = mongoTemplate.find(query, Reply.class);
		log.debug("Found {} replies for reply ID: {}", replies.size(), parentReplyId);
		return replies;
	}
}
