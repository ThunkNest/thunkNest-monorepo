package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.reputation.neo4j.ReputationChange;
import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository(value = "neoReputationRepository")
public interface ReputationRepository extends Neo4jRepository<ReputationChange, UUID> {
	
	@Query("MATCH (user:User)<-[affects:AFFECTS]-(change:ReputationChange) " +
			"WHERE change.timestamp >= datetime().epochMillis - 86400000 " +
			"WITH user, SUM(change.pointsChanged) AS dailyScore " +
			"RETURN user ORDER BY dailyScore DESC LIMIT 5")
	List<User> findTop5UsersLast24Hours();
	
}
