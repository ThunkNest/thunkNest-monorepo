package com.validate.monorepo.voteservice.service;

import com.validate.monorepo.commonlibrary.model.vote.Vote;
import com.validate.monorepo.commonlibrary.model.vote.VoteType;
import com.validate.monorepo.commonlibrary.repository.mongo.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VoteUtilityService {
	
	private final VoteRepository voteRepository;
	
	public VoteUtilityService(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}
	
	public long countVotesByPost(String postId, VoteType voteType) {
		return voteRepository.countVotesByPost(postId, voteType);
	}
	
	public long countVotesByReply(String replyId, VoteType voteType) {
		return voteRepository.countVotesByReply(replyId, voteType);
	}
	
	public boolean hasUserVoted(String userId, String postId, String replyId) {
		return voteRepository.hasUserVoted(userId, postId, replyId);
	}
	
	public List<String> findAllVotesByUser(String userId) {
		return voteRepository.findAllVotesByUser(userId);
	}
	
	public List<String> findVotesByUserAndVoteType(String userId, VoteType voteType) {
		return voteRepository.findVotesByUserAndVoteType(userId, voteType);
	}
	
	public Map<String, VoteType> getVotesForPosts(String userId, List<String> postIds) {
		if (postIds == null || postIds.isEmpty()) {
			log.info("Input postIds is null or empty.");
			return Map.of();
		}
		
		List<Vote> votes = voteRepository.findVotesByUserIdAndPostIds(userId, postIds);
		log.info("Fetched votes: {}", votes);
		
		Map<String, VoteType> voteMap = votes.stream()
				.filter(vote -> vote.postId() != null && vote.voteType() != null)
				.collect(Collectors.toMap(Vote::postId, Vote::voteType, (existing, replacement) -> existing));
		log.info("Vote map: {}", voteMap);
		
		Map<String, VoteType> result = postIds.stream()
				.filter(Objects::nonNull)
				.filter(voteMap::containsKey)
				.collect(Collectors.toMap(postId -> postId, voteMap::get));
		log.info("Result map: {}", result);
		
		return result;
	}
	
	public Map<String, VoteType> getVotesForReplies(String userId, List<String> replyIds) {
		if (replyIds == null || replyIds.isEmpty()) {
			return Map.of();
		}
		
		List<Vote> votes = voteRepository.findVotesByUserIdAndReplyIds(userId, replyIds);
		
		Map<String, VoteType> voteMap = votes.stream()
				.filter(vote -> vote.replyId() != null && vote.voteType() != null)
				.collect(Collectors.toMap(Vote::replyId, Vote::voteType, (existing, replacement) -> existing));
		
		Map<String, VoteType> result = replyIds.stream()
				.filter(Objects::nonNull)
				.filter(voteMap::containsKey)
				.collect(Collectors.toMap(replyId -> replyId, voteMap::get));
		
		return result;
	}
	
}
