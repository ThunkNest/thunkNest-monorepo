package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.reply.Reply;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReplyRepository extends Neo4jRepository<Reply, UUID> {
	
	@Query("""
        MATCH (parent:Reply {id: $parentReplyId}), (r:Reply {id: $replyId})
        MERGE (parent)-[:HAS_REPLY]->(r)
    """
	)
	void addReplyToReply(UUID parentReplyId, UUID replyId);
	
	@Query("""
    MATCH (r:Reply {id: $replyId}), (u:User {id: $userId})
    OPTIONAL MATCH (r)<-[rel:DOWNVOTED_BY]-(u)
    WITH r, u, rel
    DELETE rel
    WITH r, u, CASE WHEN rel IS NOT NULL THEN 1 ELSE 0 END AS downVoteAdjustment
    MERGE (r)<-[r2:UPVOTED_BY]-(u)
    SET r.upVoteCount = COALESCE(r.upVoteCount, 0) + 1,
        r.downVoteCount = r.downVoteCount - downVoteAdjustment
""")
	void upVoteReply(UUID replyId, UUID userId);
	
	
	@Query("""
        MATCH (r:Reply {id: $replyId})<-[rel:UPVOTED_BY]-(u:User {id: $userId})
        DELETE rel
        SET r.upVoteCount = r.upVoteCount - 1
    """
	)
	void removeUpVoteReply(UUID replyId, UUID userId);
	
	@Query("""
        MATCH (r:Reply {id: $replyId}), (u:User {id: $userId})
        OPTIONAL MATCH (r)<-[rel:UPVOTED_BY]-(u)
        DELETE rel
        WITH r, u
        MERGE (r)<-[r2:DOWNVOTED_BY]-(u)
        SET r.downVoteCount = COALESCE(r.downVoteCount, 0) + 1
        SET r.upVoteCount = CASE WHEN rel IS NOT NULL THEN r.upVoteCount - 1 ELSE r.upVoteCount END
    """
	)
	void downVoteReply(UUID replyId, UUID userId);
	
	@Query("""
        MATCH (r:Reply {id: $replyId})<-[rel:DOWNVOTED_BY]-(u:User {id: $userId})
        DELETE rel
        SET r.downVoteCount = r.downVoteCount - 1
    """
	)
	void removeDownVoteReply(UUID replyId, UUID userId);
}
