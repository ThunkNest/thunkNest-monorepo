package com.validate.monorepo.searchservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PostSyncService {
	
	private final ElasticsearchClient elasticsearchClient;
	private final PostRepository postRepository;
	
	@Autowired
	public PostSyncService(ElasticsearchClient elasticsearchClient, PostRepository postRepository) {
		this.elasticsearchClient = elasticsearchClient;
		this.postRepository = postRepository;
	}
	
	public void syncAllPosts() {
		log.info("syncAllPosts: Starting sync for all posts to elastic, start={}", new Date());
		List<Post> posts = postRepository.findAll();
		posts.forEach(this::indexPost);
		log.info("syncAllPosts: Finished sync for all posts to elastic, end={}", new Date());
	}
	
	public void indexPost(Post post) {
		try {
			if (!post.isDeleted() || post.deletedAt() == 0) {
				IndexRequest<Post> request = IndexRequest.of(builder -> builder
						.index("posts")
						.id(post.id())
						.document(post)
				);
				elasticsearchClient.index(request);
			}
		} catch (Exception e) {
			log.error("indexPost: Error indexing post in elasticsearch. Post={}", post, e);
			throw new RuntimeException(String.format("indexPost: Error indexing post in elasticsearch. Post=%s", post));
		}
	}
	
	public void deletePost(String postId) {
		try {
			DeleteRequest request = DeleteRequest.of(builder -> builder
					.index("posts")
					.id(postId)
			);
			elasticsearchClient.delete(request);
		} catch (Exception e) {
			log.error("indexPost: Error deleting indexed post in elasticsearch. postId={}", postId, e);
			throw new RuntimeException(String.format("indexPost: Error deleting indexed post in elasticsearch. postId=%s \n Exception=%s", postId, e));
		}
	}
}
