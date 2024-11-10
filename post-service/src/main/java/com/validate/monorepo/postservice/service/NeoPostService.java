package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.neo4j.PostRepository;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NeoPostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public NeoPostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public Post createPost(String title, String description, UUID authorId) {
		User author = userRepository.findById(authorId).orElseThrow(() ->
				new BadRequestException("Author does not exist"));
		
		Post post = new Post(null, title, description, false, author, List.of(),
				List.of(), List.of(), LocalDateTime.now()
		);
		
		return postRepository.save(post);
	}
}
