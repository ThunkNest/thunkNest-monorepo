package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.vote.Vote;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomVoteRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String>, CustomVoteRepository {
}
