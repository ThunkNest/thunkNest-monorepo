package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.reply.ReplyRequest;
import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	private final PostService postService;
	private final UserRepository userRepository;
	
	@Autowired
	public ReplyService(ReplyRepository replyRepository, PostService postService, UserRepository userRepository) {
		this.replyRepository = replyRepository;
		this.postService = postService;
		this.userRepository = userRepository;
	}
	
	@Transactional(readOnly = true)
	public Reply getReplyById(String replyId) {
		return replyRepository.findById(replyId).orElseThrow(() ->
				new NotFoundException(String.format("Reply with ID=%s not found", replyId)));
	}
	
	@Transactional(readOnly = true)
	public List<Reply> getAllReplies() {
		return replyRepository.findAllRepliesAndIsDeletedFalse();
	}
	
	@Transactional
	public Reply editReply(String replyId, ReplyRequest request) {
		Reply replyToUpdate = getReplyById(replyId);
		
		if (replyToUpdate.isDeleted()) throw new BadRequestException("Cannot edit a deleted reply");
		
		String replyText = request.replyText();
		
		return replyRepository.editReply(replyId, replyText, getMentionedUsersInReply(replyText));
	}
	
	@Transactional
	public Reply createReply(String postId, ReplyRequest request) {
		postService.getPostById(postId);
		Reply createdReply = addReplyToDataStore(postId, request);
		postService.addReplyToPost(postId, createdReply.id());
		
		return createdReply;
	}

	private Reply addReplyToDataStore(String postId, ReplyRequest request) {
		User author = getUserByUserName(request.authorUsername());
		
		if (author == null) throw new BadRequestException(String.format("User with username=%s does not exist", request.authorUsername()));
		
		String replyText = request.replyText();
		Reply reply = new Reply(null, replyText, 0, 0, author, postId,
				getMentionedUsersInReply(replyText), false, false, 0, Instant.now().toEpochMilli());
		return replyRepository.save(reply);
	}
	
	private User getUserByUserName(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}
	
	public List<Reply> findRepliesByTaggedUserId(String userId) {
		return replyRepository.findRepliesByTaggedUserId(userId);
	}
	
	@Transactional
	public void deleteReply(String replyId) {
		Reply replyToDelete = getReplyById(replyId);
		postService.deleteReplyFromPost(replyToDelete.parentPostId(), replyId);
		replyRepository.save(replyToDelete.deleteReply());
	}
	
	private List<User> getMentionedUsersInReply(String replyText) {
		log.info("getMentionedUsersInReply: finding tagged users");
		
		Pattern mentionPattern = Pattern.compile("@[\\w-]+");
		Matcher matcher = mentionPattern.matcher(replyText);
		
		// Collect all unique tagged usernames into a Set
		Set<String> taggedUsernames = matcher.results()
				.map(matchResult -> matchResult.group().substring(1)) // Remove '@' prefix
				.collect(Collectors.toSet());
		
		if (taggedUsernames.isEmpty()) {
			return List.of();
		}
		
		// Batch query to fetch users by username
		return userRepository.findAllByUsernameIn(taggedUsernames);
	}
	
}
