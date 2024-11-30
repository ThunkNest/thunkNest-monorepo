package com.validate.monorepo.reputationservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.reputation.ReputationChange;
import com.validate.monorepo.commonlibrary.model.reputation.TopUser;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.mongo.ReputationRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReputationService {
	
	private final ReputationRepository reputationRepository;
	private final UserRepository userRepository;
	private final CacheManager cacheManager;
	
	private static final String TOP_USERS_CACHE = "topUsers";
	private static final int UPVOTE_POINTS = 3;
	private static final int DOWNVOTE_POINTS = -1;
	
	public ReputationService(ReputationRepository reputationRepository, UserRepository userRepository, CacheManager cacheManager) {
		this.reputationRepository = reputationRepository;
		this.userRepository = userRepository;
		this.cacheManager = cacheManager;
	}
	
	@CachePut(value = TOP_USERS_CACHE)
	@Scheduled(fixedRate = 120000)
	public List<TopUser> updateTop5UsersLast24Hours() {
		log.info("updateTop5UsersLast24Hours: updating top 5 users cache");
		List<TopUser> topUsers = reputationRepository.findTop5UsersLast24Hours();
		Objects.requireNonNull(cacheManager.getCache(TOP_USERS_CACHE)).put("topUsers", topUsers);
		return topUsers;
	}
	
	public List<TopUser> get() {
		return reputationRepository.findTop5UsersLast24Hours();
	}
	
	@Cacheable(value = TOP_USERS_CACHE)
	public List<TopUser> getTop5UsersLast24HoursFromCache() {
		Object cachedValue = Objects.requireNonNull(cacheManager.getCache(TOP_USERS_CACHE)).get("topUsers");
		if (cachedValue != null) {
			return (List<TopUser>) cachedValue;
		}
		return null;
	}
	
	public void upvoteReputationIncrease(String authorId, String postId, String replyId) {
		User author = getAuthor(authorId);
		ReputationChange reputationChange = new ReputationChange(null, author, postId, replyId, UPVOTE_POINTS, Instant.now().toEpochMilli());
		
		reputationRepository.save(reputationChange);
	}
	
	public void removeUpvoteReputationIncrease(String authorId, String postId, String replyId) {
		ReputationChange changeToRemove = getReputationChange(authorId, postId, replyId);
		reputationRepository.delete(changeToRemove);
	}
	
	public void downVoteReputationDecrease(String authorId, String postId, String replyId) {
		User author = getAuthor(authorId);
		ReputationChange reputationChange = new ReputationChange(null, author, postId, replyId, DOWNVOTE_POINTS, Instant.now().toEpochMilli());
		
		reputationRepository.save(reputationChange);
	}
	
	public void removeDownVoteReputationDecrease(String authorId, String postId, String replyId) {
		ReputationChange changeToRemove = getReputationChange(authorId, postId, replyId);
		reputationRepository.delete(changeToRemove);
	}
	
	private User getAuthor(String authorId) {
		return userRepository.findById(authorId).orElseThrow(() ->
				new BadRequestException(String.format("User with ID=%s does not exist", authorId)));
	}
	
	private ReputationChange getReputationChange(String authorId, String postId, String replyId) {
		return reputationRepository.findByUserIdAndPostOrReplyId(authorId, postId, replyId).orElseThrow(() ->
				new NotFoundException("No reputation change matching given criteria"));
	}
	
}
