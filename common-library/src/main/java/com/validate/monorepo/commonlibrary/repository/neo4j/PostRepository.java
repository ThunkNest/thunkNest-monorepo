package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.neo.post.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends Neo4jRepository<Post, UUID> {}
