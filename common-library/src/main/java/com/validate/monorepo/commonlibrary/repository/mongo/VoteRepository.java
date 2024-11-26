package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.vote.mongo.Vote;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomVoteRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote, String>, CustomVoteRepository {
}
