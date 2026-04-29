package com.fx.repository.mongo;

import com.fx.dto.mongo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
