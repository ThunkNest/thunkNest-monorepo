package com.validate.monorepo.userservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	
	public UserService(UserRepository userRepository, PostRepository postRepository, ReplyRepository replyRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
	}
	
	public User getUserById(String id) {
		return userRepository.findById(id).orElseThrow(() ->
				new NotFoundException(String.format("User with Id=%s not found", id)));
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() ->
				new NotFoundException(String.format("User with email=%s not found", email)));
	}
	
	public User getUserByUsername(String userName) {
		return userRepository.findByUsername(userName).orElseThrow(() ->
				new NotFoundException(String.format("User with username=%s not found", userName)));
	}
	
	// Todo: Delete get all users method
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<User> searchUsers(String fragment) {
		if (Objects.equals(fragment, "-")) throw new IllegalArgumentException("Disallowed character search");
		return userRepository.searchByUsername(fragment);
	}
	
	public void deleteUserById(String id) {
		userRepository.deleteById(id);
	}
	
	public void upvoteReputationIncrease(String userId, VoteRequest request) {
		User author = userRepository.upvoteReputationIncrease(userId);
		if (request.postId() != null) updateAllPostsUserAuthored(author);
		else updateAllRepliesUserAuthored(author);
	}
	public void removeUpvoteReputationIncrease(String userId, VoteRequest request) {
		User author = userRepository.removeUpvoteReputationIncrease(userId);
		
		if (request.postId() != null) updateAllPostsUserAuthored(author);
		else updateAllRepliesUserAuthored(author);
	}
	public void downVoteReputationDecrease(String userId, VoteRequest request) {
		User author = userRepository.downVoteReputationDecrease(userId);
		if (request.postId() != null) updateAllPostsUserAuthored(author);
		else updateAllRepliesUserAuthored(author);
	}
	public void removeDownVoteReputationDecrease(String userId, VoteRequest request) {
		User author = userRepository.removeDownVoteReputationDecrease(userId);
		if (request.postId() != null) updateAllPostsUserAuthored(author);
		else updateAllRepliesUserAuthored(author);
	}
	
	public void updateAllPostsUserAuthored(User author) {
		postRepository.updateUserInAuthoredPosts(author);
	}
	
	public void updateAllRepliesUserAuthored(User author) {
		replyRepository.updateUserInAuthoredPosts(author);
	}
	
}
