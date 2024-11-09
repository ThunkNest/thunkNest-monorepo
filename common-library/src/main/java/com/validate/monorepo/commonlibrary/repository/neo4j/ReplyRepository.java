package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.neo.Reply;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository("NeoReplyRepo")
public interface ReplyRepository extends Neo4jRepository<Reply, Long> {}
