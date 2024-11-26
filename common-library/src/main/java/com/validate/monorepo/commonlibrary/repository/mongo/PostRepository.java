package com.validate.monorepo.commonlibrary.repository.mongo;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.repository.mongo.custom.CustomPostRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String>, CustomPostRepository {}
