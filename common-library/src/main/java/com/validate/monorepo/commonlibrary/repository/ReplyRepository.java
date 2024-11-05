package com.validate.monorepo.commonlibrary.repository;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.repository.custom.CustomReplyRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String>, CustomReplyRepository {
	
	List<Reply> findTopLevelRepliesByPostId(final String postId);
	List<Reply> findRepliesByParentReplyId(final String parentReplyId);

//	@Aggregation("{ $sample: { size: 100 } }")
//	List<Post> find100RandomPosts();
	
}
