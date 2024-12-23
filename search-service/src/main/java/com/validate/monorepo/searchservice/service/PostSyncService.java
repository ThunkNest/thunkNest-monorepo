package com.validate.monorepo.searchservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
	
	@Scheduled(cron = "0 0 0/3 * * ?", zone = "UTC")
	public void scheduledIndexingJob() {
		log.info("scheduledIndexingJob: Starting scheduled post indexing...");
		syncAllPosts();
	}
	
	public void syncAllPosts() {
		log.info("syncAllPosts: Starting sync for all posts to elastic, start={}", new Date());
		List<Post> posts = postRepository.findAll();
		posts.forEach(this::indexPost);
		log.info("syncAllPosts: Finished sync for all posts to elastic, end={}", new Date());
	}
	
	public void indexPost(Post post) {
		log.info("indexPost: indexing post with ID={}", post.id());
		try {
			if (!post.isDeleted() || post.deletedAt() == 0) {
				// Preprocess data (e.g., lowercased fields)
				Post processedPost = new Post(
						post.id(),
						post.title().toLowerCase(),
						post.description().toLowerCase(),
						post.isDeleted(),
						post.deletedAt(),
						post.isEdited(),
						post.editedAt(),
						post.upVoteCount(),
						post.downVoteCount(),
						post.openToCoFounder(),
						post.author(),
						post.replies(),
						post.createdAt()
				);
				
				IndexRequest<Post> request = IndexRequest.of(builder -> builder
						.index("posts")
						.id(processedPost.id())
						.document(processedPost)
				);
				elasticsearchClient.index(request);
			}
		} catch (Exception e) {
			log.error("indexPost: Error indexing post in elasticsearch. Post={}", post, e);
			throw new RuntimeException(String.format("indexPost: Error indexing post in elasticsearch. Post=%s", post));
		}
	}
	
	public void deletePost(String postId) {
		log.info("deletePost: deleting post from index post with ID={}", postId);
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
