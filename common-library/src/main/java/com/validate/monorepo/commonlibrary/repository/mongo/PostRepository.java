package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
//	@Query("""
//				MATCH (p:Post {id: $postId})
//				OPTIONAL MATCH (p)<-[created:CREATED]-(author:User)
//				RETURN p, collect(created), collect(author)
//		"""
//	)
//	Optional<Post> findById(@Param("postId") UUID postId);
//
//	@Query("""
//    MATCH (p:Post {id: $postId})
//    OPTIONAL MATCH (p)<-[upvote:UPVOTED_BY]-(u:User)
//    RETURN collect(u)
//""")
//	List<User> findUpvotedUsers(@Param("postId") UUID postId);
//
//	@Query("""
//    MATCH (p:Post {id: $postId})
//    OPTIONAL MATCH (p)<-[downvote:DOWNVOTED_BY]-(d:User)
//    RETURN collect(d)
//""")
//	List<User> findDownvotedUsers(@Param("postId") UUID postId);
//
//	@Query("""
//    MATCH (p:Post {id: $postId})
//    OPTIONAL MATCH (p)-[reply:HAS_REPLY]->(r:Reply)
//    RETURN collect(r)
//""")
//	List<Reply> findReplies(@Param("postId") UUID postId);
//
//	/* Voting Repository Methods for Posts*/
//
//	@Query("""
//        MATCH (p:Post {id: $postId}), (u:User {id: $userId})
//        OPTIONAL MATCH (p)<-[r:DOWNVOTED_BY]-(u)
//        WITH p, u, r
//        DELETE r
//        WITH p, u, CASE WHEN r IS NOT NULL THEN 1 ELSE 0 END AS downVoteAdjustment
//        MERGE (p)<-[r2:UPVOTED_BY]-(u)
//        SET p.upVoteCount = COALESCE(p.upVoteCount, 0) + 1,
//          p.downVoteCount = p.downVoteCount - downVoteAdjustment
//    """
//	)
//	void upVotePost(UUID postId, UUID userId);
//
//	@Query("""
//        MATCH (p:Post {id: $postId})<-[r:UPVOTED_BY]-(u:User {id: $userId})
//        DELETE r
//        SET p.upVoteCount = p.upVoteCount - 1
//    """
//	)
//	void removeUpVote(UUID postId, UUID userId);
//
//	@Query("""
//				MATCH (p:Post {id: $postId}), (u:User {id: $userId})
//				OPTIONAL MATCH (p)<-[r:UPVOTED_BY]-(u)
//				WITH p, u, r
//				DELETE r
//				WITH p, u, CASE WHEN r IS NOT NULL THEN 1 ELSE 0 END AS upVoteAdjustment
//				MERGE (p)<-[r2:DOWNVOTED_BY]-(u)
//				SET p.downVoteCount = COALESCE(p.downVoteCount, 0) + 1,
//								p.upVoteCount = p.upVoteCount - upVoteAdjustment
//		"""
//	)
//	void downVotePost(UUID postId, UUID userId);
//
//	@Query("""
//        MATCH (p:Post {id: $postId})<-[r:DOWNVOTED_BY]-(u:User {id: $userId})
//        DELETE r
//        SET p.downVoteCount = p.downVoteCount - 1
//    """
//	)
//	void removeDownVote(UUID postId, UUID userId);
//
//	@Query("""
//    MATCH (p:Post {id: $postId}), (r:Reply {id: $replyId})
//    MERGE (p)-[:HAS_REPLY]->(r)
//    MERGE (r)-[:BELONGS_TO]->(p)
//""")
//	void addReplyToPost(UUID postId, UUID replyId);
//
//	@Query("""
//        MATCH (u:User {id: $userId})-[:CREATED]->(p:Post)
//        RETURN p
//    """
//	)
//	List<Post> findAllPostsByAuthor(UUID userId);
//
//	@Query("""
//        MATCH (u:User {id: $userId})
//        OPTIONAL MATCH (u)-[:UPVOTED_BY|DOWNVOTED_BY]->(p:Post)
//        OPTIONAL MATCH (u)-[:CREATED]->(p:Post)
//        OPTIONAL MATCH (u)-[:HAS_REPLY*]->(r:Reply)-[:HAS_REPLY*]->(p:Post)
//        RETURN DISTINCT p
//    """
//	)
//	List<Post> findAllPostsUserInteractedWith(UUID userId);
}
