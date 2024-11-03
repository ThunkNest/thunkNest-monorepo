package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomReplyRepositoryImpl implements CustomReplyRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Reply> findTopLevelRepliesByPostId(String postId) {
		Query query = new Query(Criteria.where("postId").is(postId)
				.and("parentCommentId").is(null));
		return mongoTemplate.find(query, Reply.class);
	}
	
	@Override
	public List<Reply> findRepliesByReplyId(String replyId) {
		Query query = new Query(Criteria.where("parentCommentId").is(replyId));
		return mongoTemplate.find(query, Reply.class);
	}
}
