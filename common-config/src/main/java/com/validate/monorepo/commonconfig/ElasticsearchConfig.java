package com.validate.monorepo.commonconfig;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticsearchConfig {
	
	@Bean
	public ElasticsearchClient elasticsearchClient() {
		RestClient restClient = RestClient.builder(
				new HttpHost("thunk-elasticsearch-stage.up.railway.app", 443, "https")
		).build();
		
		return new ElasticsearchClient(new RestClientTransport(restClient, new JacksonJsonpMapper()));
	}
}

//@Configuration
//public class ElasticsearchConfig {
//
//	@Value("${elasticsearch.url}")
//	private String elasticsearchUrl;
//
//	@Value("${elasticsearch.username}")
//	private String username;
//
//	@Value("${elasticsearch.password}")
//	private String password;
//
//	@Bean
//	public ElasticsearchClient elasticsearchClient() {
//		// Set up the credentials provider
//		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//
//		// Set up the SSL context to allow for HTTPS connections
//		SSLContext sslContext = SSLContexts.createSystemDefault();
//
//		RestClientBuilder builder = RestClient.builder(new HttpHost(elasticsearchUrl, 443, "https"))
//				.setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) ->
//						httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
//								.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
//								.setSSLContext(sslContext));
//
//		RestClient restClient = builder.build();
//
//		// Wrap the RestClient with the Elasticsearch transport
//		RestClientTransport transport = new RestClientTransport(
//				restClient, new JacksonJsonpMapper()
//		);
//
//		return new ElasticsearchClient(transport);
//	}
//}
