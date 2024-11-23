package com.validate.monorepo.commonlibrary.repository.neo4j;

import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository(value = "neoUserRepository")
public interface UserRepository extends Neo4jRepository<User, UUID> {
	
	@Query("MATCH (u:User {id: $id}) RETURN u")
	Optional<User> findById(@Param("id") UUID id);
	
	@Query("MATCH (user:User {username: $username}) RETURN user")
	Optional<User> findByUsername(@Param("username") String username);
	
	@Query("MATCH (user:User {email: $email}) RETURN user")
	Optional<User> findByEmail(@Param("email") String email);
	
	@Query("CALL db.index.fulltext.queryNodes('usernameIndex', $usernameFragment + '*') YIELD node " +
			"RETURN node LIMIT 10")
	List<User> searchByUsername(String usernameFragment);
	
	@Query("MATCH (u:User {id: $id}) DETACH DELETE u")
	void deleteUserById(@Param("id") UUID id);
	
	@Query("MATCH (u:User) RETURN u")
	List<User> getAllUsers();
	
}
