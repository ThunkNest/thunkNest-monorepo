package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.vote.Vote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

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
		Update update = new Update().pull("replies", replyId);
		// Perform the update operation
		mongoTemplate.updateFirst(query, update, Post.class);
	}
	
	@Override
	public void updateVoteCount(String postId, long upVoteCount, long downVoteCount) {
		Query query = new Query(Criteria.where("_id").is(postId));
		Update update = new Update().set("upVoteCount", upVoteCount).set("downVoteCount", downVoteCount);
		mongoTemplate.updateFirst(query, update, Post.class);
	}
	
	@Override
	public Page<Post> findAllPostsAndIsDeletedFalse(Pageable pageable) {
		Query query = new Query(Criteria.where("isDeleted").is(false));
		List<Post> posts = mongoTemplate.find(query, Post.class);
		long total = mongoTemplate.count(query.skip(-1).limit(-1), Post.class);
		return PageableExecutionUtils.getPage(posts, pageable, () -> total);
	}
	
	@Override
	public Page<Post> findAllPostsByAuthor(String userId, Pageable pageable) {
		Query query = new Query(Criteria.where("author._id").is(userId).and("isDeleted").is(false));
		List<Post> posts = mongoTemplate.find(query, Post.class);
		long total = mongoTemplate.count(query.skip(-1).limit(-1), Post.class);
		return PageableExecutionUtils.getPage(posts, pageable, () -> total);
	}
	
	@Override
	public Page<Post> findAllPostsUserInteractedWith(String userId, Pageable pageable) {
		if (userId == null || userId.isEmpty()) {
			log.warn("findAllPostsUserInteractedWith: userId is null or empty");
			return Page.empty(); // Return an empty list for invalid input
		}
		
		// Step 1: Fetch post IDs from the votes collection
		Query voteQuery = new Query(Criteria.where("userId").is(userId).and("postId").ne(null));
		List<String> postIdsFromVotes = mongoTemplate.find(voteQuery, Vote.class).stream()
				.map(Vote::postId)
				.distinct()
				.toList();
		
		// Step 2: Fetch parentPostIds from the replies collection
		Query replyQuery = new Query(Criteria.where("author._id").is(userId).and("isDeleted").is(false));
		List<String> postIdsFromReplies = mongoTemplate.find(replyQuery, Reply.class).stream()
				.map(Reply::parentPostId)
				.distinct()
				.toList();
		
		// Step 4: Fetch post IDs from the posts collection
		Query postQuery = new Query(Criteria.where("author._id").is(userId));
		List<String> postIdsFromPosts = mongoTemplate.find(replyQuery, Post.class).stream()
				.map(Post::id)
				.distinct()
				.toList();
		
		// Step 4: Combine all post IDs
		Set<String> allPostIds = new HashSet<>(postIdsFromVotes);
		allPostIds.addAll(postIdsFromReplies);
		allPostIds.addAll(postIdsFromPosts);
		
		if (allPostIds.isEmpty()) {
			return Page.empty(); // Return an empty list if no interactions
		}
		
		// Step 4: Fetch posts using the combined post IDs and exclude deleted ones
		Query findPostsQuery = new Query(Criteria.where("_id").in(allPostIds).and("isDeleted").is(false)).with(pageable);
		List<Post> posts = mongoTemplate.find(findPostsQuery, Post.class);
		long total = mongoTemplate.count(findPostsQuery.skip(-1).limit(-1), Post.class);
		return PageableExecutionUtils.getPage(posts, pageable, () -> total);
	}
	
}
