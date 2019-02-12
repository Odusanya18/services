package com.partycravings.services.repository;

import com.partycravings.services.model.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findCommentByServiceIdOrderByCreatedAtDesc(String serviceId);
}
