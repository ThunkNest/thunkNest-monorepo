package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomReplyRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String>, CustomReplyRepository {}
