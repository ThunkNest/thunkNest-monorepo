package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomReplyRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String>, CustomReplyRepository {}
