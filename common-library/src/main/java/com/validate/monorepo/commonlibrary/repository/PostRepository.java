package com.validate.monorepo.commonlibrary.repository;

import com.validate.monorepo.commonlibrary.model.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
	
	List<Post> findByAuthor(String author);
	List<Post> findByCreatedAtBetween(long startEpochMilli, long endEpochMilli);
	
	@Aggregation("{ $sample: { size: 100 } }")
	List<Post> find100RandomPosts();
	
}
