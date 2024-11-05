package com.validate.monorepo.commonlibrary.repository.impl;

import com.validate.monorepo.commonlibrary.model.vote.Votes;
import com.validate.monorepo.commonlibrary.repository.custom.CustomVoteRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Slf4j
@Repository
public class CustomVoteRepositoryImpl implements CustomVoteRepository {
	
	private final MongoTemplate mongoTemplate;
	
	public CustomVoteRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void upvotePost(String postId, String userId) {
		log.info("upvoting post={}", postId);
		Votes initialVotes = new Votes(postId, new HashSet<>(), new HashSet<>());
		mongoTemplate.insert(initialVotes);
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").is(postId)),
				new Update()
						.addToSet("upVoters", userId)
						.pull("downVoters", userId),
				Votes.class
		);
	}
	
	@Override
	public void downVotePost(String postId, String userId) {
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").is(postId)),
				new Update()
						.addToSet("downVoters", userId)
						.pull("upVoters", userId),
				Votes.class
		);
	}
	
	@Override
	public Votes getVotesForPost(String postId) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(postId)), Votes.class);
	}
	
	@Override
	public long getUpvoteCountForPost(String postId) {
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(postId)),
				Aggregation.project().and(ArrayOperators.Size.lengthOfArray("upVoters")).as("upvoteCount")
		);
		
		AggregationResults<CountResult> result = mongoTemplate.aggregate(aggregation, "votes", CountResult.class);
		CountResult countResult = result.getUniqueMappedResult();
		return countResult != null ? countResult.getCount() : 0;
	}
	
	@Override
	public long getDownVoteCountForPost(String postId) {
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(postId)),
				Aggregation.project().and(ArrayOperators.Size.lengthOfArray("downVoters")).as("downvoteCount")
		);
		
		AggregationResults<CountResult> result = mongoTemplate.aggregate(aggregation, "votes", CountResult.class);
		CountResult countResult = result.getUniqueMappedResult();
		return countResult != null ? countResult.getCount() : 0;
	}
	
	@Getter
	@Setter
	private static class CountResult {
		private long count;
	}
	
}
