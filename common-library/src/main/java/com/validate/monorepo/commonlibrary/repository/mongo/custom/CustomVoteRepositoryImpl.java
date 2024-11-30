package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.vote.Vote;
import com.validate.monorepo.commonlibrary.model.vote.VoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomVoteRepositoryImpl implements CustomVoteRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomVoteRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void upsertVote(String userId, String postId, String replyId, VoteType voteType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if (postId != null) {
			query.addCriteria(Criteria.where("postId").is(postId));
		} else if (replyId != null) {
			query.addCriteria(Criteria.where("replyId").is(replyId));
		}
		
		Update update = new Update()
				.set("voteType", voteType)
				.set("createdAt", System.currentTimeMillis());
		if (postId != null) {
			update.set("postId", postId).unset("replyId");
		} else {
			update.set("replyId", replyId).unset("postId");
		}
		
		mongoTemplate.upsert(query, update, Vote.class);
	}
	
	@Override
	public Vote removeVote(String userId, String postId, String replyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		
		if (postId != null) {
			query.addCriteria(Criteria.where("postId").is(postId));
		} else if (replyId != null) {
			query.addCriteria(Criteria.where("replyId").is(replyId));
		}
		
		Vote deletedVote = mongoTemplate.find(query, Vote.class).get(0);
		
		mongoTemplate.remove(query, Vote.class);
		return deletedVote;
	}
	
	@Override
	public long countVotesByPost(String postId, VoteType voteType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("postId").is(postId).and("voteType").is(voteType));
		return mongoTemplate.count(query, Vote.class);
	}
	
	@Override
	public long countVotesByReply(String replyId, VoteType voteType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("replyId").is(replyId).and("voteType").is(voteType));
		return mongoTemplate.count(query, Vote.class);
	}
	
	@Override
	public boolean hasUserVoted(String userId, String postId, String replyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if (postId != null) {
			query.addCriteria(Criteria.where("postId").is(postId));
		} else if (replyId != null) {
			query.addCriteria(Criteria.where("replyId").is(replyId));
		}
		return mongoTemplate.exists(query, Vote.class);
	}
	
	@Override
	public List<String> findAllVotesByUser(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		return mongoTemplate.find(query, Vote.class).stream()
				.map(vote -> vote.postId() != null ? vote.postId() : vote.replyId())
				.collect(Collectors.toList());
	}
	
	@Override
	public List<String> findVotesByUserAndVoteType(String userId, VoteType voteType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("voteType").is(voteType));
		return mongoTemplate.find(query, Vote.class).stream()
				.map(vote -> vote.postId() != null ? vote.postId() : vote.replyId())
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Vote> findVotesByUserIdAndPostIds(String userId, List<String> postIds) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("postId").in(postIds));
		return mongoTemplate.find(query, Vote.class);
	}
	
	@Override
	public List<Vote> findVotesByUserIdAndReplyIds(String userId, List<String> replyIds) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("replyId").in(replyIds));
		return mongoTemplate.find(query, Vote.class);
	}
}
