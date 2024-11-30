package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.reputation.ReputationChange;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomReputationRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReputationRepository extends MongoRepository<ReputationChange, String>, CustomReputationRepository {}
