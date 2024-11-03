package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.repository.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.custom.CustomReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	
	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}
	
	/**
	 * Fetches the top-level replies for a given post ID.
	 * @param postId the ID of the post
	 * @return a list of top-level replies
	 */
	public List<Reply> getTopLevelRepliesByPostId(String postId) {
		log.debug("Fetching top-level replies for post ID: {}", postId);
		List<Reply> replies = replyRepository.findTopLevelRepliesByPostId(postId);
		log.debug("Retrieved {} top-level replies for post ID: {}", replies.size(), postId);
		return replies;
	}
	
	/**
	 * Fetches replies that are children of a given reply ID.
	 * @param replyId the ID of the parent reply
	 * @return a list of replies that are children of the specified reply ID
	 */
	public List<Reply> getRepliesByReplyId(String replyId) {
		log.debug("Fetching replies for reply ID: {}", replyId);
		List<Reply> replies = replyRepository.findRepliesByReplyId(replyId);
		log.debug("Retrieved {} replies for reply ID: {}", replies.size(), replyId);
		return replies;
	}
}
