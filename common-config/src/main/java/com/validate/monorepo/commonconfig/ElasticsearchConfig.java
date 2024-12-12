package com.validate.monorepo.commonconfig;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
	
	@Value("${elasticsearch.url}")
	private String elasticsearchUrl;

	@Value("${elasticsearch.username}")
	private String username;

	@Value("${elasticsearch.password}")
	private String password;
	
	@Bean
	public RestClient restClient() {
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));
		
		return RestClient.builder(new HttpHost("thunk-elasticsearch-stage.up.railway.app", 443, "https"))
				.setHttpClientConfigCallback(httpAsyncClientBuilder ->
						httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
				.build();
	}
	
	@Bean
	public RestClientTransport restClientTransport(RestClient restClient) {
		JsonpMapper jsonpMapper = new JacksonJsonpMapper();
		return new RestClientTransport(restClient, jsonpMapper);
	}
	
	@Bean
	public ElasticsearchClient elasticsearchClient(RestClientTransport restClientTransport) {
		return new ElasticsearchClient(restClientTransport);
	}
}
