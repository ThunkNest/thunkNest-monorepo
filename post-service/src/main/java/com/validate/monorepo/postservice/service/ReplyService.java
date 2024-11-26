package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.reply.ReplyRequest;
import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	@Transactional
	public void upVoteReply(String replyId, String userId) {
		replyRepository.upVoteReply(replyId, userId);
	}
	
	@Transactional
	public void removeUpVoteReply(String replyId, String userId) {
		replyRepository.removeUpVoteReply(replyId, userId);
	}
	
	@Transactional
	public void downVoteReply(String replyId, String userId) {
		replyRepository.downVoteReply(replyId, userId);
	}
	
	@Transactional
	public void removeDownVoteReply(String replyId, String userId) {
		replyRepository.removeDownVoteReply(replyId, userId);
	}
	
	@Transactional(readOnly = true)
	public Reply getReplyById(String replyId) {
		return replyRepository.findById(replyId).orElseThrow(() ->
				new NotFoundException(String.format("Reply with ID=%s not found", replyId)));
	}
	
	@Transactional(readOnly = true)
	public List<Reply> getAllReplies() {
		return replyRepository.findAll();
	}
	
	@Transactional
	public Reply updateReply(String replyId, ReplyRequest request) {
		getReplyById(replyId);
		String replyText = request.replyText();
		
		return replyRepository.updateReply(replyId, replyText, getMentionedUsersInReply(replyText));
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
		Reply reply = new Reply(null, replyText, 0, 0, author, List.of(),
				List.of(), List.of(), postId, getMentionedUsersInReply(replyText), false, Instant.now().toEpochMilli());
		return replyRepository.save(reply);
	}
	
	private User getUserByUserName(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}
	
	@Transactional
	public void deleteReply(String replyId) {
		Reply replyToDelete = getReplyById(replyId);
		postService.deleteReplyFromPost(replyToDelete.parentPostId(), replyId);
		replyRepository.deleteById(replyId);
	}
	
	private List<User> getMentionedUsersInReply(String replyText) {
		Pattern mentionPattern = Pattern.compile("@[a-zA-Z]+-[a-zA-Z]+-\\d{3}");
		Matcher matcher = mentionPattern.matcher(replyText);
		
		List<String> taggedUsernames = new ArrayList<>();
		while (matcher.find()) {
			taggedUsernames.add(matcher.group());
		}
		
		List<User> taggedUsers = taggedUsernames.stream()
				.map(this::getUserByUserName)
				.filter(Objects::nonNull)
				.toList();
		
		return taggedUsers.isEmpty() ? List.of() : taggedUsers;
	}
	
}
