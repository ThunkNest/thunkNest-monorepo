package com.validate.monorepo.searchservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.validate.monorepo.commonlibrary.model.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {
	
	private final ElasticsearchClient elasticsearchClient;
	
	@Autowired
	public SearchService(ElasticsearchClient elasticsearchClient) {
		this.elasticsearchClient = elasticsearchClient;
	}
	
//	public List<Post> searchPosts(String query, Integer limit) {
//		try {
//			SearchRequest searchRequest = SearchRequest.of(builder -> {
//				builder.index("posts")
//						.query(QueryBuilders.multiMatch(multiMatch -> multiMatch
//								.fields("title", "description")
//								.query(query)
//								.fuzziness("AUTO")
//						));
//				if (limit != null && limit > 0) {
//					builder.size(limit); // Apply the limit
//				}
//				return builder;
//			});
//
//			SearchResponse<Post> searchResponse = elasticsearchClient.search(searchRequest, Post.class);
//
//			return searchResponse.hits().hits().stream()
//					.map(Hit::source)
//					.collect(Collectors.toList());
//		} catch (Exception e) {
//			log.error("searchPosts: Error searching posts ", e);
//			return List.of();
//		}
//	}
	
	public List<Post> searchPosts(String query, Integer limit) {
		try {
			SearchRequest searchRequest = SearchRequest.of(builder -> {
				builder.index("posts")
						.query(QueryBuilders.scriptScore(scriptScore -> scriptScore
								.query(QueryBuilders.multiMatch(multiMatch -> multiMatch
										.fields(List.of(
												"title",
												"description"
										))
										.query(query)
										.fuzziness("AUTO") // Enable typo tolerance
								))
								.script(script -> script
										.inline(inline -> inline
												.source(
														"doc['upVoteCount'].value + Math.log(1 + (params.now - doc['createdAt'].value) / 1000)"
												)
												.params(Map.of("now", JsonData.of(System.currentTimeMillis())
												))
										)
								)
						));
				if (limit != null && limit > 0) {
					builder.size(limit); // Limit results
				}
				return builder;
			});
			
			SearchResponse<Post> searchResponse = elasticsearchClient.search(searchRequest, Post.class);
			
			return searchResponse.hits().hits().stream()
					.map(Hit::source)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("searchPosts: Error searching posts ", e);
			return List.of();
		}
	}
	
}
