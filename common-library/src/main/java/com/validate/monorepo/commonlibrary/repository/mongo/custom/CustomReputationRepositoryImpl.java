package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.reputation.ReputationChange;
import com.validate.monorepo.commonlibrary.model.reputation.TopUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomReputationRepositoryImpl implements CustomReputationRepository {
	
	private final MongoTemplate mongoTemplate;
	
	public CustomReputationRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<TopUser> findTop5UsersLast24Hours() {
		long last24Hours = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
		
		Aggregation aggregation = Aggregation.newAggregation(
				// Match documents with timestamp in the last 24 hours
				Aggregation.match(Criteria.where("timestamp").gte(last24Hours)),
				// Group by user.id and calculate total pointsChanged
				Aggregation.group("user.id")
						.sum("pointsChanged").as("totalPoints")
						.first("user").as("user"),
				// Sort by totalPoints in descending order
				Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalPoints")),
				// Limit to top 5 users
				Aggregation.limit(5)
		);
		
		AggregationResults<TopUser> results = mongoTemplate.aggregate(aggregation, "reputationChange", TopUser.class);
		return results.getMappedResults();
	}
	
	@Override
	public Optional<ReputationChange> findByUserIdAndPostOrReplyId(String userId, String postId, String replyId) {
		Query query = new Query(Criteria.where("user.id").is(userId));
		
		if (postId != null) query.addCriteria(Criteria.where("postId").is(postId));
		else query.addCriteria(Criteria.where("replyId").is(replyId));
		
		return Optional.ofNullable(mongoTemplate.findOne(query, ReputationChange.class));
	}
}
