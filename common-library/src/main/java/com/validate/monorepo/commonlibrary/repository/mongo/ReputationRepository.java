package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.reputation.mongo.ReputationChange;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReputationRepository extends MongoRepository<ReputationChange, String> {
//	List<User> findTop5UsersLast24Hours();

}
