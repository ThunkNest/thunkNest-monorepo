package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.post.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends Neo4jRepository<Post, UUID> {
	@Query("""
        MATCH (p:Post {id: $postId}), (u:User {id: $userId})
        OPTIONAL MATCH (p)<-[r:DOWNVOTED_BY]-(u)
        DELETE r
        WITH p, u
        MERGE (p)<-[r2:UPVOTED_BY]-(u)
        SET p.upVoteCount = COALESCE(p.upVoteCount, 0) + 1
        SET p.downVoteCount = CASE WHEN r IS NOT NULL THEN p.downVoteCount - 1 ELSE p.downVoteCount END
    """
	)
	void upVotePost(UUID postId, UUID userId);
	
	@Query("""
        MATCH (p:Post {id: $postId})<-[r:UPVOTED_BY]-(u:User {id: $userId})
        DELETE r
        SET p.upVoteCount = p.upVoteCount - 1
    """
	)
	void removeUpVote(UUID postId, UUID userId);
	
	@Query("""
        MATCH (p:Post {id: $postId}), (u:User {id: $userId})
        OPTIONAL MATCH (p)<-[r:UPVOTED_BY]-(u)
        DELETE r
        WITH p, u
        MERGE (p)<-[r2:DOWNVOTED_BY]-(u)
        SET p.downVoteCount = COALESCE(p.downVoteCount, 0) + 1
        SET p.upVoteCount = CASE WHEN r IS NOT NULL THEN p.upVoteCount - 1 ELSE p.upVoteCount END
    """
	)
	void downVotePost(UUID postId, UUID userId);
	
	@Query("""
        MATCH (p:Post {id: $postId})<-[r:DOWNVOTED_BY]-(u:User {id: $userId})
        DELETE r
        SET p.downVoteCount = p.downVoteCount - 1
    """
	)
	void removeDownVote(UUID postId, UUID userId);
	
	@Query("""
    MATCH (p:Post {id: $postId}), (r:Reply {id: $replyId})
    MERGE (p)-[:HAS_REPLY]->(r)
    MERGE (r)-[:BELONGS_TO]->(p)
""")
	void addReplyToPost(UUID postId, UUID replyId);
	
	@Query("""
        MATCH (u:User {id: $userId})-[:CREATED]->(p:Post)
        RETURN p
    """
	)
	List<Post> findAllPostsByAuthor(UUID userId);
	
	@Query("""
        MATCH (u:User {id: $userId})
        OPTIONAL MATCH (u)-[:UPVOTED_BY|DOWNVOTED_BY]->(p:Post)
        OPTIONAL MATCH (u)-[:CREATED]->(p:Post)
        OPTIONAL MATCH (u)-[:HAS_REPLY*]->(r:Reply)-[:HAS_REPLY*]->(p:Post)
        RETURN DISTINCT p
    """
	)
	List<Post> findAllPostsUserInteractedWith(UUID userId);
}
