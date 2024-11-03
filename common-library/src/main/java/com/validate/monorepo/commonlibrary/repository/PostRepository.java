package com.validate.monorepo.commonlibrary.repository;

import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.repository.custom.CustomPostRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String>, CustomPostRepository {
	
	@Aggregation("{ $sample: { size: 100 } }")
	List<Post> find100RandomPosts();
	
}
