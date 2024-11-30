package com.validate.monorepo.reputationservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ReputationService {
	
	private final CacheManager cacheManager;
	
	private static final String TOP_USERS_CACHE = "topUsers";
	
	public void postUpVote(String postId, String voterId) {
		// do nothing
	}
	
	public void postDownVote(String postId, String voterId) {
		// do nothing
	}
	
	public void replyUpVote(String replyId, String voterId) {
		Reply reply = replyRepository.findById(UUID.fromString(replyId))
				.orElseThrow(() -> new NotFoundException(String.format("Reply with id=%s not found", replyId)));
		
		User replyAuthor = reply.author();
		
		if (isUpVoteByAuthor(reply.parentPost(), voterId)) {
			replyAuthor.upVoteReputationIncreaseFromAuthor();
			userRepository.save(replyAuthor);
			ReputationChange repChange = new ReputationChange(null, replyAuthor, 5, LocalDateTime.now());
			reputationRepository.save(repChange);
		}
		else {
			replyAuthor.upVoteReputationIncrease();
			userRepository.save(replyAuthor);
			ReputationChange repChange = new ReputationChange(null, replyAuthor, 3, LocalDateTime.now());
			reputationRepository.save(repChange);
		}
		
	}
	
	public void replyDownVote(String replyId, String voterId) {
		Reply reply = replyRepository.findById(UUID.fromString(replyId))
				.orElseThrow(() -> new NotFoundException(String.format("Reply with id=%s not found", replyId)));
		
		User replyAuthor = reply.author();
		userRepository.save(replyAuthor);
		ReputationChange repChange = new ReputationChange(null, replyAuthor, -1, LocalDateTime.now());
		reputationRepository.save(repChange);
	}
	
	@CachePut(value = TOP_USERS_CACHE)
	@Scheduled(fixedRate = 120000)
	public List<User> updateTop5UsersLast24Hours() {
		List<User> topUsers = reputationRepository.findTop5UsersLast24Hours();
		Objects.requireNonNull(cacheManager.getCache(TOP_USERS_CACHE)).put("topUsers", topUsers);
		return topUsers;
	}
	
	@Cacheable(value = TOP_USERS_CACHE)
	public List<User> getTop5UsersLast24HoursFromCache() {
		Object cachedValue = Objects.requireNonNull(cacheManager.getCache(TOP_USERS_CACHE)).get("topUsers");
		if (cachedValue != null) {
			return (List<User>) cachedValue;
		}
		return null;
	}
	
	private boolean isUpVoteByAuthor(Post post, String voterId) {
		return post.author().id().toString().equals(voterId);
	}
	
}
