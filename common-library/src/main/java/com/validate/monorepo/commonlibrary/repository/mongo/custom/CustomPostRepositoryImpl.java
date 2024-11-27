package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.model.vote.mongo.Vote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
	public List<Post> findAllPostsAndIsDeletedFalse() {
		Query query = new Query(Criteria.where("isDeleted").is(false));
		return mongoTemplate.find(query, Post.class);
	}
	
	@Override
	public List<Post> findAllPostsByAuthor(String userId) {
		Query query = new Query(Criteria.where("author._id").is(userId).and("isDeleted").is(false));
		return mongoTemplate.find(query, Post.class);
	}
	
	@Override
	public List<Post> findAllPostsUserInteractedWith(String userId) {
		if (userId == null || userId.isEmpty()) {
			log.warn("findAllPostsUserInteractedWith: userId is null or empty");
			return List.of(); // Return an empty list for invalid input
		}
		
		// Step 1: Fetch post IDs from the votes collection
		Query voteQuery = new Query(Criteria.where("userId").is(userId).and("postId").ne(null));
		voteQuery.fields().include("postId");
		List<String> postIdsFromVotes = mongoTemplate.find(voteQuery, Vote.class).stream()
				.map(Vote::postId)
				.distinct()
				.toList();
		
		// Step 2: Fetch parentPostIds from the replies collection
		Query replyQuery = new Query(Criteria.where("author._id").is(userId));
		replyQuery.fields().include("parentPostId");
		List<String> postIdsFromReplies = mongoTemplate.find(replyQuery, Reply.class).stream()
				.map(Reply::parentPostId)
				.distinct()
				.toList();
		
		// Step 3: Combine all post IDs
		Set<String> allPostIds = new HashSet<>(postIdsFromVotes);
		allPostIds.addAll(postIdsFromReplies);
		
		if (allPostIds.isEmpty()) {
			return List.of(); // Return an empty list if no interactions
		}
		
		// Step 4: Fetch posts using the combined post IDs and exclude deleted ones
		Query postQuery = new Query(Criteria.where("_id").in(allPostIds).and("isDeleted").is(false));
		return mongoTemplate.find(postQuery, Post.class);
	}
	
}
